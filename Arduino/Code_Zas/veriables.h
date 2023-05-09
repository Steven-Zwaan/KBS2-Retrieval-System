#define VRY_PIN  A3 // Arduino pin connected to VRY pin
#define SW_PIN   A0  // Arduino pin connected to SW  pin



#define UP_THRESHOLD    200
#define DOWN_THRESHOLD  700

#define COMMAND_NO     0x00
#define COMMAND_UP     0x04
#define COMMAND_DOWN   0x08

ezButton button(SW_PIN);

//ezButton button(SW_PIN);

// Variabelen
const int zPin = A4;
int ZPWM = 11;
int ZDir = 13;
bool Vooruit = LOW;
bool Achteruit = HIGH;
bool dirState = 1; //0 is Y; 1 = Z;


int zValue = 0; // To store value of the Z axis
 
//int bValue = 0; // To store value of the button
int bValue = 0; // To store value of the button
int command = COMMAND_NO; //stop