// X
void motorXleft(){
  int motorSpeed = 255 - map(xValue, 0, 512, 0, 255);
      digitalWrite(XDir, LOW);
      analogWrite(XPWM, motorSpeed);
}

void motorXright(){
  int motorSpeed = map(xValue, 512, 1023, 0, 255);
      digitalWrite(XDir, HIGH);
      analogWrite(XPWM, motorSpeed);
}

void motorXstop(){
  analogWrite(XPWM, 0);
      command;
}
