int readIR() {
  // Print the measured distance to the serial monitor:
  int distance_cm = irSensor.distance();
  Serial.print("Mean distance: ");
  Serial.print(irSensor.distance());
  Serial.println(" cm");

  // Read IR distance and return
  return irSensor.distance();
}
