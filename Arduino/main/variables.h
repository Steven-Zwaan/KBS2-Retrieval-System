#include <ezButton.h>

#define VRX_PIN  A2 // Arduino pin connected to joystick x-axis (VRX) pin
#define SW_PIN   7  // Arduino pin connected to joystick button (SW)  pin

#define LS_LEFT  6 // Arduino pin connected to limit switch (left) pin 
#define LS_RIGHT 5 // Arduino pin connected to limit switch (right) pin


#define LEFT_THRESHOLD  200 // Minimum the joystick has to move before action
#define RIGHT_THRESHOLD 700 // Maximum the joystick has to move before action

#define COMMAND_NO     0x00 // 00000000
#define COMMAND_LEFT   0x01 // 00000001
#define COMMAND_RIGHT  0x02 // 00000010
#define COMMAND_UP     0x04 // 00000100
#define COMMAND_DOWN   0x08 // 00001000

ezButton joystickButton(SW_PIN);
ezButton limitSwitchR(LS_RIGHT);
ezButton limitSwitchL(LS_LEFT);

// Variabelen
const int zPin = A5;
int XPWM = 11;
int XDir = 13;
int xEnc = 2;

bool Links = LOW;
bool Rechts = HIGH;

bool borderHitRight = false;
bool borderHitLeft = false;
bool zAxisCalibrated = false;

bool zAs = false;

bool calibrate = true;

bool manual = true;

bool Noodstop = false;
int NoodstopIngedrukt = 4;

int xPos = 0;

int xPosBoxes[] = {100, 800, 1600, 2300, 3000};

int xValue = 0; // To store value of the X axis
int bValue = 0; // To store value of the button
int command = COMMAND_NO; //stop