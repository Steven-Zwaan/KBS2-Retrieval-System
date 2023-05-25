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
    if (calibrate) {
      if(!zAxisCalibrated){
        if (!zAxisMessageSent) {
          sendTransmission("CSZ");
          zAxisMessageSent = true;
        }
      } else {
        if (!borderHitLeft){
          motorXleft();
        } else {
          motorXstop();
          xPos = 0;
          if (yAxisCalibrated) {
            sendTransmission("CF");
            calibrate = false;
            zAs = false;
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
            case 3: // bewegeing x en y as en dan z as
              if(motorXgoTo(xPosBoxes[hmi_var1])){
                if(!messageSent){
                  sendTransmission("TX");
                  messageSent = true;
                }
                actionXCompleted = true;
              }
              if(actionZCompleted){
                Serial.println(600);
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