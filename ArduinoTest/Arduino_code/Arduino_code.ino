#define LED_PIN (int8_t)13 //pin number LED is connected to
int potPin = A0;

boolean sent = false; //testing

char data; //variable to store incoming data from JAVA 

void setup() {
  pinMode(LED_PIN, OUTPUT);
  pinMode(potPin, INPUT);
  Serial.begin(9600);
  Serial.setTimeout(50);

  Serial.println("readyToWork");
}

void loop() {
  if(Serial.available()>0){ //if data has been written to the Serial stream
    data=Serial.read();
  
    if(data == '1') 
      digitalWrite(LED_PIN,HIGH);
    else if(data == '0') 
      digitalWrite(LED_PIN,LOW);
    else 
      digitalWrite(LED_PIN,HIGH);
  }

//  int value = map(analogRead(potPin), 0, 1023, 0, 100);
//  Serial.println(value);
//  delay(100);

  
  
}

void receiveData() {
  
}
