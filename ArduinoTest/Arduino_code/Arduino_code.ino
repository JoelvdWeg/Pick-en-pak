String data; // String voor inkomende data van de HMI-applicatie

void setup() {
  pinMode(LED_PIN, OUTPUT);
  pinMode(potPin, INPUT);
  Serial.begin(9600);
  Serial.setTimeout(50);

  Serial.println("readyToWork");
}

void loop() {
  
if (Serial.available() > 0 { //Seriele data ontvangen
  data = Serial.readString();
  if (data == "readyToSend") { //De HMI-applicatie is klaar om een instructie te versturen
    Serial.println("readyToReceive");
    data = Serial.readString();
    if (data == "") {
      
    }
  }
}
  
  
}
