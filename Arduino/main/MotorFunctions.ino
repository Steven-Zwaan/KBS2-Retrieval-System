void motorXleft(){
  int motorSpeed = 255 - map(xValue, 0, 1023, 0, 255);
      digitalWrite(XDir, LOW);
      analogWrite(XPWM, motorSpeed);
}

void motorXright(){
  int motorSpeed = map(xValue, 512, 1023, 0, 255);
      digitalWrite(XDir, HIGH);
      analogWrite(XPWM, motorSpeed);
}

void motorYup(){
   int motorSpeed = 255 - map(yValue, 0, 512, 0, 255); // z as aruino
      digitalWrite(YDir, LOW);
      analogWrite(YPWM, motorSpeed);
}

void motorYdown(){
  int motorSpeed = map(yValue, 513, 1023, 0, 255); // z as aruino
      digitalWrite(YDir, HIGH);
      analogWrite(YPWM, motorSpeed);
}

void motorXstop(){
  analogWrite(XPWM, 0); 
      command;
}

void motorYstop(){
  analogWrite(YPWM, 0); // z as aruino
      command;
}