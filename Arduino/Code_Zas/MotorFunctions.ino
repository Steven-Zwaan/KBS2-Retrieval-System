// Z
void motorZforward()
{
  int motorSpeed = 255 - map(yValue, 0, 512, 0, 255);
    digitalWrite(ZDir, LOW);
    analogWrite(ZPWM, motorSpeed);
}

void motorZbackward()
{
   int motorSpeed = map(yValue, 513, 1023, 0, 255);
    digitalWrite(ZDir, HIGH);
    analogWrite(ZPWM, motorSpeed);
}

void motorZstop()
{
  analogWrite(ZPWM, 0);
    command;
}

// Y
void motorYup(){
   int motorSpeed = 255 - map(yValue, 0, 512, 0, 255);
      digitalWrite(YDir, LOW);
      analogWrite(YPWM, motorSpeed);
      Omhoog = true;
      Omlaag = false;
}

void motorYdown(){
  int motorSpeed = map(yValue, 513, 1023, 0, 255);
      digitalWrite(YDir, HIGH);
      analogWrite(YPWM, motorSpeed);
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