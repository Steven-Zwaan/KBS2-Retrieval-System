// X
void motorXleft(){
  // int motorSpeed = 255 - map(xValue, 0, 512, 0, 255);
      digitalWrite(XDir, LOW);
      analogWrite(XPWM, 255);
      Links = true;
      Rechts = false;
}

void motorXright(){
  // int motorSpeed = map(xValue, 512, 1023, 0, 255);
      digitalWrite(XDir, HIGH);
      analogWrite(XPWM, 255);
      Links = false;
      Rechts = true;
}

void motorXstop(){
  analogWrite(XPWM, 0);
      command;
}

void encoderXadd(){
  if (Links) {
    xPos--;
  } else if (Rechts){
    xPos++;
  }
}

bool motorXgoTo(int xPosition){
  if (xPos < xPosition - 22){
    motorXright();
    return 0;
  } else if (xPos > xPosition + 22){
    motorXleft();
    return 0;
  } else {
    motorXstop();
    return 1;
  }
}
