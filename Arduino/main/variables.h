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

bool Noodstop = false;
int NoodstopIngedrukt = 4;

int xValue = 512; // To store value of the X axis
int yValue = 512; // To store value of the Y axis
int bValue = 0; // To store value of the button
int command = COMMAND_NO; //stop