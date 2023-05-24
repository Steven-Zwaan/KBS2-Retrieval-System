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

#define BUS 9

#define NOODSTOP 10
#define RESET 4

#define ZPIN A5
#define XPWM 11
#define XDIR 13
#define XENC 2

ezButton joystickButton(SW_PIN);
ezButton limitSwitchR(LS_RIGHT);
ezButton limitSwitchL(LS_LEFT);

ezButton noodstopButton(NOODSTOP);
ezButton resetButton(RESET);

// Variabelen
// const int zPin = A5;
// int XPWM = 11;
// int XDir = 13;
// int xEnc = 2;

bool Links = LOW;
bool Rechts = HIGH;

bool borderHitRight = false;
bool borderHitLeft = false;

bool zAxisCalibrated = false;
bool yAxisCalibrated = false;

bool zAxisMessageSent = false;

bool zAs = false;

bool calibrate = true;

bool manual = false;

bool Noodstop = false;
// int NOODSTOPBUTTON = 4;
int bValue = 0;

int xPos = 0;

int xPosBoxes[] = {100, 800, 1600, 2300, 3000};

int xValue = 0; // To store value of the X axis
// int joystickButton = 0; // To store value of the button
int command = COMMAND_NO; //stop

int hmi_action = 0;
int hmi_var1 = 0;
int hmi_var2 = 0;
const int BUFFER_SIZE = 3;
char buf[BUFFER_SIZE];
String message = "";
bool messageSent = false;
bool actionCompleted = true;
bool actionYCompleted = false;