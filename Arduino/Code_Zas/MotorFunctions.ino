void motorZup()
{
  int motorSpeed = 255 - map(zValue, 0, 512, 0, 255);
    digitalWrite(ZDir, LOW);
    analogWrite(ZPWM, motorSpeed);
}

void motorZdown()
{
   int motorSpeed = map(zValue, 513, 1023, 0, 255);
    digitalWrite(ZDir, HIGH);
    analogWrite(ZPWM, motorSpeed);
}

void motorZstop()
{
  analogWrite(ZPWM, 0);
    command;
}

void motorYup(){
   int motorSpeed = 255 - map(yValue, 0, 512, 0, 255);
      digitalWrite(YDir, LOW);
      analogWrite(YPWM, motorSpeed);
}

void motorYdown(){
  int motorSpeed = map(yValue, 513, 1023, 0, 255);
      digitalWrite(YDir, HIGH);
      analogWrite(YPWM, motorSpeed);
}

void motorYstop(){
  analogWrite(YPWM, 0);
      command;
}