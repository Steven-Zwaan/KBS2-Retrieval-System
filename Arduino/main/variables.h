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

#define BUS 9 // bus voor wire communicatie

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
bool Links = LOW;
bool Rechts = HIGH;

bool borderHitRight = false;
bool borderHitLeft = false;

bool zAxisCalibrated = false;
bool yAxisCalibrated = false;

bool zAxisMessageSent = false;
bool messageYsend = false;

bool zAs = false;

bool calibrate = true;

bool manual = false;

bool Noodstop = false;
int bValue = 0;

int xPos = 0; // huidige xPos positie

int xPosBoxes[] = {150, 880, 1600, 2300, 3030}; // xPos coordinaten voor de verschillende posities in de stellage

int xValue = 0; // To store value of the X axis
int command = COMMAND_NO; //stop

int hmi_action = 0;
int hmi_var1 = 0;
int hmi_var2 = 0;
const int BUFFER_SIZE = 3;
char buf[BUFFER_SIZE];
String message = "";
bool messageSent = false;
bool actionXCompleted = false;
bool actionYCompleted = false;
bool actionZCompleted = false;

int y_position = 0; // huidige locatie Y-as
const unsigned long SEND_POSITION_INTERVAL = 1000; // ms
unsigned long lastSentPositionTime = 0; // tijd laatst verstuurde locatie in millisecondes