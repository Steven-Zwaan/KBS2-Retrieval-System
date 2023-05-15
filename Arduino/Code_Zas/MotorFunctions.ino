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
    return 1;
  }
}