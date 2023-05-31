
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

#define BUS 1 // bus voor wire communicatie

#define RED A0
#define ORANGE 4
#define GREEN 7

#define MODE 10

ezButton limitSwitchT(LS_TOP);
ezButton limitSwitchB(LS_BOTTOM);
ezButton modeSwitch(MODE);

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

bool zAs = false; // bool of z-as actief is; true = z-as; false = y-as;

int yPos = 0; // huidige yPos positie
int yPositionCurrent = 0;
bool packagePicked = false;
bool inPosition = false;
bool done = false;
bool doneZ = false;
bool moveCompleted = false;

bool messageSend = false;

bool manual = false;
bool zReturned = false;
bool yPositioned = false;

int yPosBoxes[] = {210, 730, 1250, 1760, 2270}; // yPos coordinaten voor de verschillende posities in de stellage
int zPosBoxes[] = {18, 13, 9}; // z-as afstanded voor de verschillende grab punten

int yValue = 0; // To store value of the Y axis
int bValue = 0; // To store value of the button
int command = COMMAND_NO; //stop

int hmi_y = 0;
int hmi_z = 0;
String recieved = "";

bool x_arrived = false;
int current_products = 0;

const unsigned long SEND_POSITION_INTERVAL = 500; // ms
unsigned long lastSentPositionTime = 0; // tijd laatst verstuurde locatie in millisecondes