#include <Wire.h>
#include <ezButton.h>
#include "veriables.h"
#include "functions.h"


void setup() {
  // put your setup code here, to run once:
 Serial.begin(9600);
 Wire.begin(2);
  button.setDebounceTime(50); // set debounce time to 50 milliseconds
  TCCR2B = TCCR2B & B11111000 | B00000111;
pinMode (ZPWM,OUTPUT);
pinMode (ZDir, OUTPUT);
pinMode(VRY_PIN, INPUT);
pinMode(zPin, INPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  button.loop(); // MUST call the loop() function first

  Wire.onReceive(handler);

  // read analog X and Y analog values
  //zValue = analogRead(zPin);

 // converts the analog value to commands
  // reset commands
  command = COMMAND_NO;

  

  // NOTE: AT A TIME, THERE MAY BE NO COMMAND, ONE COMMAND OR TWO COMMANDS

  // print command to serial and process command
  Serial.println(zValue);
  
}

void handler(int bytes) {
  if(dirState) {
    zValue = Wire.read();
    
      // check up/down commands
    if (zValue < UP_THRESHOLD)
      command = command | COMMAND_UP;
    else if (zValue > DOWN_THRESHOLD)
      command = command | COMMAND_DOWN; 
      if (command & COMMAND_UP) {
      // TODO: add your task here
      motorZup();
    } else if (command & COMMAND_DOWN) {
      // TODO: add your task here
      motorZdown();
    } else  {
      motorZstop();
    }
  } else {
      yValue = Wire.read();
    // check left/right commands
      if (yValue < LEFT_THRESHOLD)
        command = command | COMMAND_LEFT;
      else if (yValue > RIGHT_THRESHOLD)
        command = command | COMMAND_RIGHT;

      // check up/down commands
      if (yValue < UP_THRESHOLD)
      command = command | COMMAND_UP;
      else if (yValue > DOWN_THRESHOLD)
        command = command | COMMAND_DOWN;
    if (command & COMMAND_UP) {
       motorYup();

      } else if (command & COMMAND_DOWN) { // z as aruino
        motorYdown();

      } else  {
        motorYstop();
      }
  }
}