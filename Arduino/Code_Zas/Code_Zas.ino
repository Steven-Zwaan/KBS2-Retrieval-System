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

  attachInterrupt(digitalPinToInterrupt(2), encoderYadd, RISING);
}

void loop() {
  // put your main code here, to run repeatedly:
  limitSwitchT.loop(); // MUST call the loop() function first
  limitSwitchB.loop();

  // read analog Y analog values
  yValue = analogRead(VRY_PIN);

 // converts the analog value to commands
  // reset commands
  command = COMMAND_NO;

  if (noodstop) {
    command;
    motorZstop();
    motorYstop();
  } else if (!noodstop) {
    if(calibrate){

    } else {
      if (zAs){
      // check up/down commands
        if (yValue < FORWARD_THRESHOLD)
          command = command | COMMAND_FORWARD;
        else if (yValue > BACKWARD_THRESHOLD)
          command = command | COMMAND_BACKWARD;

        // NOTE: AT A TIME, THERE MAY BE NO COMMAND, ONE COMMAND OR TWO COMMANDS

        // print command to serial and process command
        if (((command & COMMAND_FORWARD) & COMMAND_FORWARD) && (readIR() < 18)) {
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

        } else if (((command & COMMAND_DOWN) & COMMAND_DOWN) && !borderHitBottom) {
          motorYdown();

        } else  {
          motorYstop();
        }
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
  Serial.print(" Y: ");
  Serial.println(yPos);
}

void RequestEvent(){
  if(calibrateZ){
    if(readIR() != 5){
      motorZbackward();
    } else {
      motorZstop();
      Wire.write("CZF");
      calibrateZ = false;
    }
  } else if(calibrateY) {
    if(!borderHitBottom){
      motorYdown();
    } else {
      motorYstop();
      Wire.write("CYF");
      calibrateY = false;
    }
  }
}

void RecieveEvent(int howMany){
  String recieved = "";
  for(int i = 0; i < howMany; i++){
    recieved += (char)Wire.read();
  }
  
  if (recieved == "ZT"){
    zAs = true;    
  } else if (recieved == "ZF") {
    zAs = false;
  }

  if (recieved == "NT"){
    noodstop = true;
  }

  if(recieved == "CSZ"){
    calibrateZ = true;
  }
  if(recieved == "CSY"){
    calibrateY = true;
  }
}



