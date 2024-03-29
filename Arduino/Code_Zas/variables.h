
#define VRY_PIN  A3 // Arduino pin connected to VRY pin
#define SW_PIN   A0  // Arduino pin connected to SW  pin

#define LS_BOTTOM 5
#define LS_TOP    6

#define IR_PIN A2 //InfraRed pin
#define model 430 // SharpIR model based on info below
/* Model :
  GP2Y0A02YK0F --> 20150
  GP2Y0A21YK0F --> 1080
  GP2Y0A710K0F --> 100500
  GP2YA41SK0F --> 430
*/
SharpIR irSensor = SharpIR(IR_PIN, model); // new SharpIR object

#define FORWARD_THRESHOLD     200
#define BACKWARD_THRESHOLD    700
#define UP_THRESHOLD          200
#define DOWN_THRESHOLD        700

#define COMMAND_NO         0x00
#define COMMAND_UP         0x01
#define COMMAND_DOWN       0x02
#define COMMAND_FORWARD    0x04
#define COMMAND_BACKWARD   0x08

#define BUS 1

#define RED A0
#define ORANGE 4
#define GREEN 7

#define MODE 10

ezButton limitSwitchT(LS_TOP);
ezButton limitSwitchB(LS_BOTTOM);
ezButton modeSwitch(MODE);

//ezButton button(SW_PIN);

// Variabelen
const int zPin = A4;
int ZPWM = 11;
int ZDir = 13;

int YPWM= 3;
int YDir = 12;

bool Omhoog = false;
bool Omlaag = false;

bool borderHitTop = false;
bool borderHitBottom = false;

bool noodstop = false;

bool calibrate = true;
bool calibrateZ = false;
bool calibrateY = false;

bool zAs = false;

int yPos = 0;
int yPositionCurrent = 0;
bool packagePicked = false;
bool inPosition = false;
bool done = false;

bool manual = true;

int yPosBoxes[] = {145, 625, 1160, 1650, 2200};
int zPosBoxes[] = {17, 12, 9}; //12 might be 13 and 8 might be 9

//int bValue = 0; // To store value of the button
int yValue = 0; // To store value of the Y axis
int bValue = 0; // To store value of the button
int command = COMMAND_NO; //stop