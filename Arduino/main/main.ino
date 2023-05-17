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

  TCCR2B = TCCR2B & B11111000 | B00000111;
  pinMode (XPWM,OUTPUT);
  pinMode (XDir, OUTPUT);
  pinMode (xEnc, INPUT);
  
  pinMode(VRX_PIN, INPUT);

  pinMode(zPin, OUTPUT);
  
  pinMode(NOODSTOPBUTTON, INPUT_PULLUP);

  attachInterrupt(digitalPinToInterrupt(2), encoderXadd, RISING);
}

void loop() {
  // put your main code here, to run repeatedly:
  joystickButton.loop(); // MUST call the loop() function first
  
  //Noodstop check
  if (!digitalRead(NOODSTOPBUTTON) && !Noodstop)
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

  if(Noodstop) {
    motorXstop(); 
  } else if (!Noodstop){
    if (calibrate) {
      if(!zAxisCalibrated){
        if (!zAxisMessageSent) {
          Serial.println("Test - Preparing transmission Z-axis");
          Wire.beginTransmission(9);
          Wire.write("CSZ");
          Wire.endTransmission();
          Serial.println("Test - Transmission send Z-axis");
          zAxisMessageSent = true;
        }
      } else {
        if (!borderHitLeft){
          motorXleft();
        } else {
          motorXstop();
          xPos = 0;
          if (yAxisCalibrated) {
            Wire.beginTransmission(9);
            Wire.write("CF");
            Wire.endTransmission();
            calibrate = false;
            zAs = false;
            Serial.println("Calibration process complete");
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
        if (motorXgoTo(xPosBoxes[2])){
          Serial.println("Succes!");
        }
      }
    }
  }
  

  joystickButton = joystickButton.getState();

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
    xPos = 0;
  } else if (borderHitLeft == true) {
    borderHitLeft = false;
  }

}

void ReceiveEvent(int howMany){
  String recieved = "";
  for(int i = 0; i < howMany; i++){
    recieved += (char)Wire.read();
  }

  if (recieved == "CZF") {
    zAxisCalibrated = true;
    Serial.println("CZF recieved");
  }

  if (recieved == "CYF") {
    yAxisCalibrated = true;
    Serial.println("CYF recieved");
  }
}