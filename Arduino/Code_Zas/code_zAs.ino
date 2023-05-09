#include <Wire.h>
#include <ezButton.h>
#include "veriables.h"
#include "functions.h"


void setup() {
  // put your setup code here, to run once:
 Serial.begin(9600) ;
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

    // read analog X and Y analog values
 
zValue = analogRead(zPin);

 // converts the analog value to commands
  // reset commands
  command = COMMAND_NO;

  // check up/down commands
  if (zValue < UP_THRESHOLD)
    command = command | COMMAND_UP;
  else if (zValue > DOWN_THRESHOLD)
    command = command | COMMAND_DOWN;

  // NOTE: AT A TIME, THERE MAY BE NO COMMAND, ONE COMMAND OR TWO COMMANDS

  // print command to serial and process command
  Serial.println(zValue);
  if (command & COMMAND_UP) {
    // TODO: add your task here
    motorZup();

  } else if (command & COMMAND_DOWN) {
    // TODO: add your task here
    motorZdown();

  } else  {
  
    motorZstop();
  
  }


  

}