char str[5];
const int ledPin = 2;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(ledPin, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (Serial.available()) {
    int data = Serial.parseInt();
    if (data == 1) {
      digitalWrite(ledPin, HIGH);
    } else if (data == 0) {
      digitalWrite(ledPin, LOW);
    }
  }
}
