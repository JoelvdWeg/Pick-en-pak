char str[5];

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  itoa(analogRead(A0), str, 10);
  Serial.write(str, 5);
  delay(10);
}
