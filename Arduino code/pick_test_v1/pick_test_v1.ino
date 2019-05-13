//setup SoftwareSerial
#include <SoftwareSerial.h>
SoftwareSerial mySerial(10, 11); // RX, TX

//motor pins
int directionX = 4;
int directionY = 7;
int speedX = 6;
int speedY = 6;

//sensor pins
int sensorX = A0;
int sensorY = A1;

//variabelen voor coordinaten
int currentX = 0;
int currentY = 0;
int goalX = 0;
int goalY = 0;

//diversen setup variabelen
int maxSpeedX = 80; //maximum snelhied voor het bewegen over de X-as

//boolean xZwart = false;
//boolean yZwart = false;

char incommingCommandChar = 'n';
// n = not set
// u = up
// d = down
// l = left
// r = right
// p = push
// q = pull
// c = coordinate (cxy)
// z = reset

void setup() {
  Serial.begin(9600);
  mySerial.begin(9600);

  //stel output pins in
  pinMode(directionX, OUTPUT);
  pinMode(directionY, OUTPUT);
  pinMode(speedX, OUTPUT);
  pinMode(speedY, OUTPUT);

  //stel input pins in
  pinMode(sensorX, INPUT);
  pinMode(sensorY, INPUT);

  Serial.println("--- klaar ---");
}

void loop() {

  checkForCommands();

}

void checkForCommands() {

  if (Serial.available()) {
    //lees char uit serial buffer
    incommingCommandChar = Serial.read();

    if (incommingCommandChar == 'l' || incommingCommandChar == 'r' || incommingCommandChar == 's' || incommingCommandChar == 'u' || incommingCommandChar == 'd') { // handmatig besturen
      moveRobot(incommingCommandChar);
      Serial.print("HANDMATIG, invoer: ");
      Serial.println(incommingCommandChar);
      incommingCommandChar = 'n';
    }
  }
  
}

void moveRobot(char chosenDirection) {
  switch (chosenDirection) {
      case 'l': //left
        digitalWrite(directionX, HIGH);
        analogWrite(speedX, maxSpeedX);
      break;
      case 'r': //right
        digitalWrite(directionX, LOW);
        analogWrite(speedX, maxSpeedX);
      break;
      case 'u': // up
        digitalWrite(directionY, HIGH);
        analogWrite(speedY, 100);
      break;
      case 'd': // down
        digitalWrite(directionY, LOW);
        analogWrite(speedY, 60);
      break;
      default:
        analogWrite(speedX, 0);
        analogWrite(speedY, 0);
      break;
    }
}
