#include <Wire.h>
#include <ezButton.h>
#include "variables.h"
#include "functions.h"

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600) ;
  // setup as master adruino
  Wire.begin(1);
  Wire.onReceive(ReceiveEvent);
  
  joystickButton.setDebounceTime(50); // set debounce time to 50 milliseconds
  limitSwitchR.setDebounceTime(50);
  limitSwitchL.setDebounceTime(50);
  noodstopButton.setDebounceTime(50);
  resetButton.setDebounceTime(50);

  TCCR2B = TCCR2B & B11111000 | B00000111;
  pinMode (XPWM,OUTPUT);
  pinMode (XDIR, OUTPUT);
  pinMode (XENC, INPUT);
  
  pinMode(VRX_PIN, INPUT);
  
  pinMode(NOODSTOP, INPUT_PULLUP);

  attachInterrupt(digitalPinToInterrupt(2), encoderXadd, RISING);
}

void loop() {
  // put your main code here, to run repeatedly:
  joystickButton.loop(); // MUST call the loop() function first
  noodstopButton.loop();
  resetButton.loop();
  
  //Noodstop check
  if (noodstopButton.isPressed() && !Noodstop)
  {
    Noodstop = true;
    sendTransmission("NT");  
    command;
  }

// read analog X and Y analog values
  xValue = analogRead(VRX_PIN);


// reset commands
  command = COMMAND_NO;

  if(Noodstop) {
    motorXstop(); 
    //Serial.println("NOODSTOP");
  } else if (!Noodstop){
    if (calibrate) { // Kijk of kalibratie aan staat
      if(!zAxisCalibrated){ // kijk of de z-as nog niet gekalibreerd is
        if (!zAxisMessageSent) { // kijk of het z-as kalibratie bericht nog niet verstuurd is.
          sendTransmission("CSZ"); // verstuur start z-as kalibratie.
          zAxisMessageSent = true; // zet z-as kalibratie verstuurd op true
        }
      } else { // else als de z-as gekalibreerd is.
        if (!borderHitLeft){ //kijk of de robot de linker knop niet aanraakt.
          motorXleft(); // laat de robot naar links gaan.
        } else {
          motorXstop(); // stop de x-as motor.
          xPos = 0; // zet xpositie op 0
          if (yAxisCalibrated) {  // check of de y-as gekalibreerd is.
            sendTransmission("CF"); // verstuur kalibratie klaar bericht. 
            calibrate = false; // zet kalibratie uit/
            zAs = false; // zet z-as uit.
          }
        }
      }
    } else {
      if (manual) {
        if (!zAs) {
        // check left/right commands
          if (xValue < LEFT_THRESHOLD)
            command = command | COMMAND_LEFT;
          else if (xValue > RIGHT_THRESHOLD)
            command = command | COMMAND_RIGHT;

          // print command to serial and process command
          if (((command & COMMAND_LEFT) & COMMAND_LEFT) && (!borderHitLeft)) {
            motorXleft();
          
          } else if (((command & COMMAND_RIGHT) & COMMAND_RIGHT) && (!borderHitRight)) {
            motorXright();

          } else {
            motorXstop();

          }
        }
      } else {

        communcationHandler(); // executes when hmi sends message

        switch (hmi_action){
            case 1: //bewegen x en y as
              if(motorXgoTo(xPosBoxes[hmi_var1])){
                actionXCompleted = true;
              }
              if(actionYCompleted && actionXCompleted){
                Serial.println(600);
              }              
              break;
            case 2: // bewegen z as
              if(actionZCompleted){
                Serial.println(600);
              }  
              break;   
            case 3: // beweging x en y as en dan z as
              if(motorXgoTo(xPosBoxes[hmi_var1])){ // ga naar de meegegeven x-as positie
                if(!messageSent){ // controlleer of bericht nog niet verstuurd is
                  sendTransmission("TX"); // verstuur T actie X compleet
                  messageSent = true; // zet bericht verstuurd op true
                }
                actionXCompleted = true; // zet X actie op compleet
              }
              if(actionZCompleted){ // check of Z actie compleet is
                Serial.println(600); // verstuur 600, oftewel actie compleet
              }
              break;
            default:
              break;
          }
      }
    }
  }
  

  bValue = joystickButton.getState();

  if (joystickButton.isPressed() && zAs == false) {
    zAs = true;
    sendTransmission("ZT");
  } else if (joystickButton.isPressed()) {
    zAs = false;
    sendTransmission("ZF");
  }

  limitSwitchR.loop();

  int stateR = limitSwitchR.getState();

  if(stateR == HIGH)
  {
    borderHitRight = true;
  } else if (borderHitRight == true) {
    borderHitRight = false;
  }

  limitSwitchL.loop();

  int stateL = limitSwitchL.getState();

  if(stateL == HIGH)
  {
    borderHitLeft = true;
    xPos = 0;
  } else if (borderHitLeft == true) {
    borderHitLeft = false;
  }

  if (resetButton.isPressed()){
    Noodstop = false;
    calibrate = true;
    
    zAxisCalibrated = false;
    yAxisCalibrated = false;
    zAxisMessageSent = false;
    sendTransmission("NF");    
  }
  // Serial.println(xPos);
}

void ReceiveEvent(int howMany){
  String recieved = "";
  for(int i = 0; i < howMany; i++){
    recieved += (char)Wire.read();
  }

  if (recieved == "TC") {
    actionZCompleted = true;
    // Serial.println("COMPLETE");
  }

  if (recieved == "MC") {
    actionYCompleted = true;
    // Serial.println("COMPLETE");
  }

  if (recieved == "GC") {
    actionZCompleted = true;
    // Serial.println("COMPLETE");
  }

  if (recieved == "MS") {
    manual = !manual; 
  }

  if (recieved == "CZF") {
    zAxisCalibrated = true;
    //Serial.println("CZF recieved");
  }

  if (recieved == "CYF") {
    yAxisCalibrated = true;
    //Serial.println("CYF recieved");
  }
}

void communcationHandler() {
  if (Serial.available()) {
    Serial.println(100); 

    Serial.readBytes(buf, BUFFER_SIZE);
    hmi_action = (int)buf[0];
    hmi_var1 = (int)buf[1];
    hmi_var2 = (int)buf[2];
  
    messageSent = false;
    actionXCompleted = false;
    actionYCompleted = false;
    actionZCompleted = false;

    switch(hmi_action){
      case 1: // Bewegen naar vakje (x,y)
        sendTransmission("M" + (String)hmi_var2);
        break;
      case 2: // oppakken product op huidige locatie (z)
        sendTransmission("G" + (String)hmi_var1);
        break;
      case 3: // bewegen naar vakje (x,y) en dan oppakken product op huidige locatie (z)
        sendTransmission("T" + (String)hmi_var2);
        break;
      default:

        break;          
    }
    // actionCompleted = false;
    // messageYsend = false;
    // Serial.println(200);
  }
}