#include <ezButton.h>
#include "variables.h"
#include "functions.h"

void setup() {
  // put your setup code here, to run once:
 Serial.begin(9600) ;
  button.setDebounceTime(50); // set debounce time to 50 milliseconds
  TCCR2B = TCCR2B & B11111000 | B00000111;
pinMode (XPWM,OUTPUT);
pinMode (XDir, OUTPUT);
pinMode (YDir, OUTPUT); // z as aruino
pinMode (YPWM, OUTPUT); // z as aruino
pinMode(VRX_PIN, INPUT);
pinMode(VRY_PIN, INPUT);
pinMode(zPin, OUTPUT);
pinMode(NoodstopIngedrukt, INPUT_PULLUP);

}

void loop() {
  // put your main code here, to run repeatedly:
  button.loop(); // MUST call the loop() function first
  Serial.println(!digitalRead(NoodstopIngedrukt));
  //Noodstop check
  if (!digitalRead(NoodstopIngedrukt) && !Noodstop)
  {
    Noodstop = true;
    command;
  }
  

  
    // read analog X and Y analog values
 xValue = analogRead(VRX_PIN);
 yValue = analogRead(VRY_PIN);

  //Serial.println(xValue);
 // converts the analog value to commands
  // reset commands
  command = COMMAND_NO;
  if(Noodstop)
  {
    motorYstop();
    motorXstop();
  }   else if (!Noodstop){
    
    if (!zAs) {
    // check left/right commands
      if (xValue < LEFT_THRESHOLD)
        command = command | COMMAND_LEFT;
      else if (xValue > RIGHT_THRESHOLD)
        command = command | COMMAND_RIGHT;

      // check up/down commands
      if (yValue < UP_THRESHOLD)
      command = command | COMMAND_UP;
      else if (yValue > DOWN_THRESHOLD)
        command = command | COMMAND_DOWN;

      // print command to serial and process command
      if (command & COMMAND_LEFT) {
        motorXleft();
      
      }  else if (command & COMMAND_RIGHT) {
        motorXright();

      }  else  {
        motorXstop();

      }
  
      if (command & COMMAND_UP) {
       motorYup();

      } else if (command & COMMAND_DOWN) { // z as aruino
        motorYdown();

      } else  {
        motorYstop();
      }
    } else {
      Serial.println(yValue);
      analogWrite(zPin, yValue);
    
    }
  }


  bValue = button.getState();

 if (button.isPressed() && zAs == false) {
    zAs = true;
  } else if (button.isPressed()) {
    zAs = false;
  }

}



