const int ledPin = 2;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  while (!Serial) {
    ; //wait for serial
  }
  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, HIGH);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (Serial.available()) {
    char data = Serial.read();
    if (data == '1') {
      digitalWrite(ledPin, HIGH);
    } else if (data == '0') {
      digitalWrite(ledPin, LOW);
    }
  }
}
