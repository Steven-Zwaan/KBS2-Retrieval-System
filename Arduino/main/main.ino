#include <Wire.h>
#include <ezButton.h>
#include "variables.h"
#include "functions.h"


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600) ;
  // setup as master adruino
  Wire.begin();
  
  button.setDebounceTime(50); // set debounce time to 50 milliseconds
  TCCR2B = TCCR2B & B11111000 | B00000111;
  pinMode (XPWM,OUTPUT);
  pinMode (XDir, OUTPUT);
  pinMode(VRX_PIN, INPUT);
  pinMode(zPin, OUTPUT);
  pinMode(NoodstopIngedrukt, INPUT_PULLUP);
}

void loop() {
  // put your main code here, to run repeatedly:
  button.loop(); // MUST call the loop() function first
  
  //Noodstop check
  if (!digitalRead(NoodstopIngedrukt) && !Noodstop)
  {
    Noodstop = true;
    Wire.beginTransmission(9);
    Wire.write("NT");
    Wire.endTransmission();    
    command;
  }

  // read analog X and Y analog values
  xValue = analogRead(VRX_PIN);


  // converts the analog value to commands
  // reset commands
  command = COMMAND_NO;

  if(Noodstop)
  {
    motorXstop(); 
  } else if (!Noodstop){

    if (!zAs) {
    // check left/right commands
      if (xValue < LEFT_THRESHOLD)
        command = command | COMMAND_LEFT;
      else if (xValue > RIGHT_THRESHOLD)
        command = command | COMMAND_RIGHT;

      // print command to serial and process command
      if ((command & COMMAND_LEFT) & COMMAND_LEFT) {
        motorXleft();
      
      } else if ((command & COMMAND_RIGHT) & COMMAND_RIGHT) {
        motorXright();

      } else {
        motorXstop();

      }
  
    }
  }


  bValue = button.getState();

  if (button.isPressed() && zAs == false) {
    zAs = true;
    Wire.beginTransmission(9);
    Wire.write("ZT");
    Wire.endTransmission();
  } else if (button.isPressed()) {
    zAs = false;
    Wire.beginTransmission(9);
    Wire.write("ZF");
    Wire.endTransmission();
  }
}