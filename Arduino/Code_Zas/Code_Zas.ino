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

  button.setDebounceTime(50); // set debounce time to 50 milliseconds
  TCCR2B = TCCR2B & B11111000 | B00000111;
  pinMode (ZPWM,OUTPUT);
  pinMode (ZDir, OUTPUT);
  pinMode(VRY_PIN, INPUT);
  pinMode(zPin, INPUT);
  pinMode (YDir, OUTPUT);
  pinMode (YPWM, OUTPUT);
  pinMode(VRY_PIN, INPUT);  
}

void loop() {
  // put your main code here, to run repeatedly:
  button.loop(); // MUST call the loop() function first

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
      if ((command & COMMAND_UP) & COMMAND_UP) {
        motorYup();

      } else if ((command & COMMAND_DOWN) & COMMAND_DOWN) {
        motorYdown();

      } else  {
        motorYstop();
      }
    }
  }
}

void RequestEvent(){
  // if(calibrate){
  //   if(readIR() != 5){
  //     motorZbackward();
  //   } else {
  //     motorZstop();
  //     Wire.write("CZF");
  //   }
  // }
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

  if(recieved == "CS"){
    calibrate = true;
    Serial.print("CS1");
    while(readIR() != 5){
      Serial.println("CS2");
      motorZbackward();
      // Serial.print("calibrating Z");
    } 
      calibrate = false;
      motorZstop();
      Serial.print("FInished calibrating Z");
      Wire.beginTransmission(1);
      Wire.write("CZF");
      Wire.endTransmission();
  
  }
}