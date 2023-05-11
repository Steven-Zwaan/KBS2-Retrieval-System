#include <Wire.h>
#include <ezButton.h>
#include "variables.h"
#include "functions.h"


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600) ;
  // setup as master adruino
  Wire.begin(6);
  Wire.onReceive(ReceiveEvent);
  
  joystickButton.setDebounceTime(50); // set debounce time to 50 milliseconds
  limitSwitchR.setDebounceTime(50);
  limitSwitchL.setDebounceTime(50);

  TCCR2B = TCCR2B & B11111000 | B00000111;
  pinMode (XPWM,OUTPUT);
  pinMode (XDir, OUTPUT);
  pinMode (xEnc, INPUT);
  
  pinMode(VRX_PIN, INPUT);

  pinMode(zPin, OUTPUT);
  
  pinMode(NoodstopIngedrukt, INPUT_PULLUP);

  attachInterrupt(digitalPinToInterrupt(2), encoderXadd, RISING);
}

void loop() {
  // put your main code here, to run repeatedly:
  joystickButton.loop(); // MUST call the loop() function first
  
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


// reset commands
  command = COMMAND_NO;

  if (calibrate) {
    if (!zAxisSafe) {
                Serial.print(zAxisSafe);

      Wire.beginTransmission(9);
      Wire.write("NS");
      Wire.endTransmission();
    } else {
      if (!borderHitLeft){
        motorXleft();
      } else {
        motorXstop();
        xPos = 0;
        calibrate = false;
      }
    }
  } else {
    if(Noodstop) {
      motorXstop(); 
    } else if (!Noodstop){
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
        if (motorXgoTo(xPosBoxes[2])){
          Serial.println("Succes!");
        }
      }
    }
  }
 


  bValue = joystickButton.getState();

  if (joystickButton.isPressed() && zAs == false) {
    zAs = true;
    Wire.beginTransmission(9);
    Wire.write("ZT");
    Wire.endTransmission();
  } else if (joystickButton.isPressed()) {
    zAs = false;
    Wire.beginTransmission(9);
    Wire.write("ZF");
    Wire.endTransmission();
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

} else if (borderHitLeft == true) {
  borderHitLeft = false;
}



Serial.println(xPos);
// Serial.println(borderHitLeft);
// Serial.println(borderHitRight);

}

void ReceiveEvent(int howMany){
  String received = "";
  for(int i = 0; i < howMany; i++){
    received += (char)Wire.read();
  }
  if (received = "AS"){
    zAxisSafe = true;
  }
}