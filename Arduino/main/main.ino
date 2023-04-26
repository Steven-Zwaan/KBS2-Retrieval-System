#include <ezButton.h>

#define VRX_PIN  A2 // Arduino pin connected to VRX pin
#define VRY_PIN  A3 // Arduino pin connected to VRY pin
#define SW_PIN   7  // Arduino pin connected to SW  pin


#define LEFT_THRESHOLD  200
#define RIGHT_THRESHOLD 700
#define UP_THRESHOLD    200
#define DOWN_THRESHOLD  700

#define COMMAND_NO     0x00
#define COMMAND_LEFT   0x01
#define COMMAND_RIGHT  0x02
#define COMMAND_UP     0x04
#define COMMAND_DOWN   0x08

ezButton button(SW_PIN);

// Variabelen
const int zPin = A5;
int XPWM = 11;
int XDir = 13;
int YDir = 12;
int YPWM= 3;
bool Links = LOW;
bool Rechts = HIGH;

bool zAs = false;


int xValue = 0; // To store value of the X axis
int yValue = 0; // To store value of the Y axis
int bValue = 0; // To store value of the button
int command = COMMAND_NO; //stop

void setup() {
  // put your setup code here, to run once:
 Serial.begin(9600) ;
  button.setDebounceTime(50); // set debounce time to 50 milliseconds
  TCCR2B = TCCR2B & B11111000 | B00000111;
pinMode (XPWM,OUTPUT);
pinMode (XDir, OUTPUT);
pinMode (YDir, OUTPUT);
pinMode (YPWM, OUTPUT);
pinMode(VRX_PIN, INPUT);
pinMode(VRY_PIN, INPUT);
pinMode(zPin, OUTPUT);

}

void loop() {
  // put your main code here, to run repeatedly:
  button.loop(); // MUST call the loop() function first

    // read analog X and Y analog values
 xValue = analogRead(VRX_PIN);
 yValue = analogRead(VRY_PIN);

 // converts the analog value to commands
  // reset commands
  command = COMMAND_NO;
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

    // NOTE: AT A TIME, THERE MAY BE NO COMMAND, ONE COMMAND OR TWO COMMANDS

    // print command to serial and process command
    if (command & COMMAND_LEFT) {
      int motorSpeed = 255 - map(xValue, 0, 1023, 0, 255);
      digitalWrite(XDir, LOW);
      analogWrite(XPWM, motorSpeed);

      //TODO: add your task here
    }  else if (command & COMMAND_RIGHT) {
      
      int motorSpeed = map(xValue, 512, 1023, 0, 255);
      digitalWrite(XDir, HIGH);
      analogWrite(XPWM, motorSpeed);
    } else  {
      analogWrite(XPWM, 0);
      command;

    }
  
    if (command & COMMAND_UP) {
      // TODO: add your task here
      int motorSpeed = 255 - map(yValue, 0, 512, 0, 255);
      digitalWrite(YDir, LOW);
      analogWrite(YPWM, motorSpeed);

    } else if (command & COMMAND_DOWN) {
      // TODO: add your task here
      int motorSpeed = map(yValue, 513, 1023, 0, 255);
      digitalWrite(YDir, HIGH);
      analogWrite(YPWM, motorSpeed);

    } else  {
      analogWrite(YPWM, 0);
      command;
    }
  } else {
    Serial.println(yValue);
    analogWrite(zPin, yValue);
    
  }

  bValue = button.getState();


 if (button.isPressed() && zAs == false) {
    zAs = true;
  } else if (button.isPressed()) {
    zAs = false;
  }

 //analogWrite(XPWM, 0);
 //digitalWrite(XDir, LOW);
  

}



