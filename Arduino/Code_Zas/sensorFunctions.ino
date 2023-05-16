int readIR() {
  // Print the measured distance to the serial monitor:
  // int distance_cm = irSensor.distance();
  // Serial.print("Mean distance: ");
  // Serial.print(irSensor.distance());
  // Serial.println(" cm");

  // Read IR distance and return
  return irSensor.distance();
}

void sendTransmission(String message) {
  Wire.beginTransmission(BUS);
  Wire.write(message.c_str());
  Wire.endTransmission();
}

void updateLEDS(){
  if (noodstop) {
    analogWrite(RED,1023);
    digitalWrite(ORANGE,LOW);
    digitalWrite(GREEN,LOW);

  } else {
    if (manual){
      analogWrite(RED,0);
      digitalWrite(ORANGE,HIGH);
      digitalWrite(GREEN,LOW);
    } else {
      analogWrite(RED,0);
      digitalWrite(ORANGE,LOW);
      digitalWrite(GREEN,HIGH);
    }
  }
  
}
