#include <ezButton.h>

#define VRX_PIN  A2 // Arduino pin connected to VRX pin
// #define VRY_PIN  A3 // Arduino pin connected to VRY pin
#define SW_PIN   7  // Arduino pin connected to SW  pin


#define LEFT_THRESHOLD  200
#define RIGHT_THRESHOLD 700

#define COMMAND_NO     0x00
#define COMMAND_LEFT   0x01
#define COMMAND_RIGHT  0x02
#define COMMAND_UP     0x04
#define COMMAND_DOWN   0x08

ezButton button(SW_PIN);

ezButton limitSwitchR(5);
ezButton limitSwitchL(6);

// Variabelen
const int zPin = A5;
int XPWM = 11;
int XDir = 13;

bool Links = LOW;
bool Rechts = HIGH;

bool zAs = false;

bool Noodstop = false;
int NoodstopIngedrukt = 4;

int xValue = 0; // To store value of the X axis
int bValue = 0; // To store value of the button
int command = COMMAND_NO; //stop