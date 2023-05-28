// Z
void motorZforward()
{
  // int motorSpeed = 255 - map(yValue, 0, 512, 0, 255);
    digitalWrite(ZDir, LOW);
    analogWrite(ZPWM, 255);
}

void motorZbackward()
{
  //  int motorSpeed = map(yValue, 513, 1023, 0, 255);
    digitalWrite(ZDir, HIGH);
    analogWrite(ZPWM, 255);
}

void motorZstop()
{
  analogWrite(ZPWM, 0);
    command;
}

// Y
void motorYup(){
  //  int motorSpeed = 255 - map(yValue, 0, 512, 0, 255);
      digitalWrite(YDir, LOW);
      analogWrite(YPWM, 255);
      Omhoog = true;
      Omlaag = false;
}

void motorYdown(){
  // int motorSpeed = map(yValue, 513, 1023, 0, 255);
      digitalWrite(YDir, HIGH);
      analogWrite(YPWM, 255);
      Omhoog = false;
      Omlaag = true;
}

void motorYstop(){
  analogWrite(YPWM, 0);
      command;
}

void encoderYadd(){
  if (Omlaag) {
    yPos--;
  } else if (Omhoog){
    yPos++;
  }
}

bool motorYgoTo(int yPosition){
  // Serial.println("in automatic motor");
  if (yPos < yPosition - 22){
    motorYup();
    return 0;
  } else if (yPos > yPosition + 22){
    motorYdown();
    return 0;
  } else {
    motorYstop();
    yPositionCurrent = yPosition;
    done = true;
    return 1;
  }
}

bool motorZpickUp(int zPosition){
  while(!packagePicked){ // kijk of het product nog niet gegrepen is
    while(!inPosition){ // loop terwijl de z-as nog niet in positie is
      if (readIR() < zPosition){ // check of de z-as positie kleiner is dan eind positie
        motorZforward(); // laat z-as vooruit gaan
      } else if (readIR() > zPosition){ // check of de z-as positie groter is dan eind positie
       motorZbackward(); // laat z-as achteruit gaan
      } else { // else als de z-as in positie is
       motorZstop(); // stop z-as motor
       yPositioned = false; // zet y positie op false
       zReturned = false; // zet z-as returned op false
       inPosition = true; // zet dat z-as in positie is
      }
    }
    if(motorYgoTo(yPosBoxes[hmi_y] + 100)){ // laat motor naar huidige y-as positie gaan + 100 omhoog
      yPositioned = true; // zet y positie op true
    }
    if(yPositioned){ // controleer of y-as in positie is
      while(!zReturned){ / loop als z-as niet op start positie is
      motorZbackward(); // laat z-as achteruit gaan
        if(readIR() == 5){ // actie als z-as op 5(cm) is oftewel op start positie
          motorZstop(); // stop z-as motor
          zReturned = true; // zet z-as returned op true
          packagePicked = true; // zet product gegrepen op true
          doneZ = true; // zet done actie z-as op true
          return 1; // return 1 dat de functie uitgevoerd is
        }
      }
    }
    
    
  }
}