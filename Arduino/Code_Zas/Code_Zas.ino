#include <SharpIR.h>
#include <Wire.h>
#include <ezButton.h>
#include <SharpIR.h>
#include "variables.h"
#include "functions.h"


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600) ;

  // start connection between arduino's
  Wire.begin(9);
  // Set trigger for slave when recieving
  Wire.onReceive(RecieveEvent);
  Wire.onRequest(RequestEvent);

  // set debounce time to 50 milliseconds
  limitSwitchT.setDebounceTime(50); 
  limitSwitchB.setDebounceTime(50); 

  TCCR2B = TCCR2B & B11111000 | B00000111;

  pinMode (ZPWM,OUTPUT);
  pinMode (ZDir, OUTPUT);
  pinMode(VRY_PIN, INPUT);
  pinMode(zPin, INPUT);
  pinMode (YDir, OUTPUT);
  pinMode (YPWM, OUTPUT);
  pinMode(VRY_PIN, INPUT);  

  pinMode(RED, OUTPUT);
  pinMode(ORANGE, OUTPUT);
  pinMode(GREEN, OUTPUT);

  attachInterrupt(digitalPinToInterrupt(2), encoderYadd, RISING);
}

void loop() {
  // put your main code here, to run repeatedly:
  limitSwitchT.loop(); // MUST call the loop() function first
  limitSwitchB.loop();
  modeSwitch.loop();

  updateLEDS();

  // read analog Y analog values
  yValue = analogRead(VRY_PIN);

 // converts the analog value to commands
  // reset commands
  command = COMMAND_NO;

  if (noodstop) {
    command;
    motorZstop();
    motorYstop();
    // Serial.print("NOODSTOP");
  } else if (!noodstop) {
    if(calibrate){ // Kijk of kalibratie aan staat
      if (calibrateZ) { // Kijk of z-as kalibratie aan staat
        if(readIR() != 5){ // kijk of de afstand op z-as niet gelijk is aan 5cm oftewel het start punt
          motorZbackward(); // Laat de z-as motor achteruit gaan.
        } else { // else als de z-as gekalibreerd is.
          motorZstop(); // Stop de z-as motor
          sendTransmission("CZF"); // Verstuur data Z-as kalibratie klaar is
          calibrateZ = false; // zet z-as kalibratie op uit
          zAs = false; // zet besturing z-as uit.
          calibrateY = true; // zet y-as kalibratie aan
        }
      } else if (calibrateY) { // kijk of y-as kalibratie aan staat
        if(!borderHitBottom){ //kijk of de robot de bottom knop niet raakt.
          motorYdown(); // laat de y-as naar beneden gaan.
        } else {
          motorYstop(); // stop de y-as motor.
          sendTransmission("CYF"); // vestuur dat y-as kalibratie klaar is.
          calibrateY = false; // zet y-as kalibratie uit.
        }
      }

    } else {
      if(manual){
        if(zAs){
        // check up/down commands
          if (yValue < FORWARD_THRESHOLD)
            command = command | COMMAND_FORWARD;
          else if (yValue > BACKWARD_THRESHOLD)
            command = command | COMMAND_BACKWARD;

          // NOTE: AT A TIME, THERE MAY BE NO COMMAND, ONE COMMAND OR TWO COMMANDS

          // print command to serial and process command
          if (((command & COMMAND_FORWARD) & COMMAND_FORWARD) && (readIR() < 17)) {
            // TODO: add your task here
            motorZforward();

          } else if (((command & COMMAND_BACKWARD) & COMMAND_BACKWARD) && (readIR() > 5)) {
            // TODO: add your task here
            motorZbackward();

          } else  {
            motorZstop();
          
          }
        } else {
          // check up/down commands
          if (yValue < UP_THRESHOLD)
            command = command | COMMAND_UP;
          else if (yValue > DOWN_THRESHOLD)
            command = command | COMMAND_DOWN;

          // print command to serial and process command 
          if (((command & COMMAND_UP) & COMMAND_UP) && !borderHitTop) {
            motorYup();
            // Serial.print(" Y: ");
            // Serial.println(yPos);

          } else if (((command & COMMAND_DOWN) & COMMAND_DOWN) && !borderHitBottom) {
            motorYdown();
            // Serial.print(" Y: ");
            // Serial.println(yPos);

          } else  {
            motorYstop();
          }
        } 
      } else {
        // Serial.println(hmi_y);
        if (!done && (recieved == "M0" || recieved == "M1" || recieved == "M2" || recieved == "M3" || recieved == "M4")){
          if (motorYgoTo(yPosBoxes[hmi_y]) && !messageSend) {
            sendTransmission("MC");
            messageSend = true;
          }
        } 

        if (!doneZ && (recieved == "T0" || recieved == "T1" || recieved == "T2" || recieved == "T3" || recieved == "T4")){
          // if(motorYgoTo(yPosBoxes[hmi_y])){
          //   if(x_arrived){
          //     if(motorZpickUp(zPosBoxes[hmi_z])){
          //     } 
          //   }
          // }
          if (motorYgoTo(yPosBoxes[hmi_y])) {
          }
          if(done){
            delay(100);
            if(x_arrived){
              delay(100);
              Serial.println(55);
              if(motorZpickUp(zPosBoxes[hmi_z])) {
                  Serial.println(99);
                  sendTransmission("TC");
                  messageSend = true;  
                  current_products++;        
              } 
            }
          }
            
        } 
        // else if(motorYgoTo(yPosBoxes[hmi_y]) && done && !moveCompleted){
        //   // Serial.println("Succes!");
          
        //   moveCompleted = true;
        // }

        if (!doneZ && (recieved == "G0" || recieved == "G1" || recieved == "G2")){
          if (motorZpickUp(zPosBoxes[hmi_z]) && !messageSend) {
              sendTransmission("GC");
              messageSend = true;
              current_products++;            
          } 
        } 
        // else if(motorZpickUp(zPosBoxes[hmi_z]) && done){
        //   sendTransmission("MC");
        // }
        // if(motorZpickUp(zPosBoxes[0])){
        //   // Serial.println("Succes!");
        // }
      }
    }
  }
  
  int stateT = limitSwitchT.getState();

  if(stateT == HIGH)
  {
    borderHitTop = true;

  } else if (borderHitTop == true) {
    borderHitTop = false;
  }


  int stateB = limitSwitchB.getState();

  if(stateB == HIGH)
  {
    borderHitBottom = true;
    yPos = 0;
  } else if (borderHitBottom == true) {
    borderHitBottom = false;
  }

  if (modeSwitch.isPressed()){
    sendTransmission("MS");
    manual = !manual; 
  }
  // Serial.println(yPos);
}

void RequestEvent(){
  
}

void RecieveEvent(int howMany){
  recieved = "";
  for(int i = 0; i < howMany; i++){
    recieved += (char)Wire.read();
  }
  Serial.println(recieved);

  if (recieved == "M0"){
    hmi_y = 0;
    done = false;
    messageSend = false;
  } else if (recieved == "M1"){
    hmi_y = 1;
    done = false;
    messageSend = false;
  } else if (recieved == "M2"){
    hmi_y = 2;
    done = false;
    messageSend = false;
  } else if (recieved == "M3"){
    hmi_y = 3;
    done = false;
    messageSend = false;
  } else if (recieved == "M4"){
    hmi_y = 4;
    done = false;
    messageSend = false;
  }

  if (recieved == "T0"){
    hmi_y = 0;
    done = false;
    doneZ = false;
    messageSend = false;
    x_arrived = false;
    packagePicked = false;
    inPosition = false;
    hmi_z = current_products;
  } else if (recieved == "T1"){
    hmi_y = 1;
    done = false;
    doneZ = false;
    messageSend = false;
    x_arrived = false;
    packagePicked = false;
    inPosition = false;
    hmi_z = current_products;
  } else if (recieved == "T2"){
    hmi_y = 2;
    done = false;
    doneZ = false;
    messageSend = false;
    x_arrived = false;
    packagePicked = false;
    inPosition = false;
    hmi_z = current_products;
  } else if (recieved == "T3"){
    hmi_y = 3;
    done = false;
    doneZ = false;
    messageSend = false;
    x_arrived = false;
    packagePicked = false;
    inPosition = false;
    hmi_z = current_products;
  } else if (recieved == "T4"){
    hmi_y = 4;
    done = false;
    doneZ = false;
    messageSend = false;
    x_arrived = false;
    packagePicked = false;
    inPosition = false;
    hmi_z = current_products;
  }

  if (recieved == "G0"){
    hmi_z = 0;
    packagePicked = false;
    inPosition = false;
    doneZ = false;
    messageSend = false;
  } else if (recieved == "G1"){
    hmi_z = 1;
    packagePicked = false;
    inPosition = false;
    doneZ = false;
    messageSend = false;
  } else if (recieved == "G2"){
    hmi_z = 2;
    packagePicked = false;
    inPosition = false;;
    doneZ = false;
    messageSend = false;
  }
  
  if (recieved == "ZT"){
    zAs = true;    
  } else if (recieved == "ZF") {
    zAs = false;
  }

  if (recieved == "NT"){
    noodstop = true;
    
  } else if (recieved == "NF"){
    noodstop = false;
    calibrate = true;
    calibrateZ = false;
    calibrateY = false;
  }

  if(recieved == "CSZ"){
    calibrateZ = true;
  }
  
  if(recieved == "CSY"){
    calibrateY = true;
  }

  if (recieved == "CF"){
    calibrate = false;
  }

  if (recieved == "TX"){
    x_arrived = true;
  }
}



