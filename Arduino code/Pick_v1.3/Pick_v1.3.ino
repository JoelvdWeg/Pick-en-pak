#include <SoftwareSerial.h>
char str[10];
SoftwareSerial mySerial(10, 11); // RX, TX

int xSnelheid = 5;
int xRichting = 4;
int xMaxSnelheid = 80;
int xSensor = A0;

boolean xDoelBereikt = false;
boolean xZwart = false;
int xDoelStop = -1; // -1 betekent not set.
int xHuidigeStop = 0;
int xIncomming;

int ySnelheid = 6;
int yRichting = 7;
int ySensor = A1;

boolean yDoelBereikt = false;
boolean yZwart = false;
int yDoelStop = -1; // -1 betekent not set.
int yHuidigeStop = 0;
int yIncomming;

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
  
  pinMode(xRichting, OUTPUT);

  Serial.println("--- klaar ---");
}

void loop() {
  if (Serial.available() > 0) {
    
    incommingCommandChar = Serial.read();
    
    if (incommingCommandChar == 'l' || incommingCommandChar == 'r' || incommingCommandChar == 's' || incommingCommandChar == 'u' || incommingCommandChar == 'd') { // handmatig besturen
      translate(incommingCommandChar);
      Serial.print("HANDMATIG, invoer: ");
      Serial.println(incommingCommandChar);
      incommingCommandChar = 'n';
    }
    else if (incommingCommandChar == 'p') { // handmatig pushen
      Serial.print("HANDMATIG, invoer: ");
      Serial.println(incommingCommandChar);
      push();
      incommingCommandChar = 'n';
    }
    else if (incommingCommandChar == 'c') { // automatisch op basis van coordinaten
      Serial.print("\nAUTOMATISCH, invoer: ");
      Serial.println(incommingCommandChar);

      checkForIncomingCoordinate();
      
      if (xIncomming >= 0 && xIncomming < 5 && yIncomming >= 0 && yIncomming < 5) {
        xDoelStop = xIncomming;
        xDoelBereikt = false;
        yDoelStop = yIncomming;
        yDoelBereikt = false;
        
        Serial.print("NAAR STOP x");
        Serial.print(xDoelStop);
        Serial.print(" y");
        Serial.print(yDoelStop);
        
        Serial.print(", huidige stop: x");
        Serial.print(xHuidigeStop);
      
        Serial.print(" y");
        Serial.println(yHuidigeStop);
      }
      else {
        Serial.print("buiten bereik");
      }
    }
    else if (incommingCommandChar = 'z') { // reset values
      xDoelStop = -1;
      yDoelStop = -1;
      xHuidigeStop = 0;
      yHuidigeStop = 0;
      incommingCommandChar = 'n';
      Serial.print("\nreset\n");
    }

    delay(50);
  }
  
  if (!xDoelBereikt) { // Go to destiny in x direction
    moveX();
  }
  else if (!yDoelBereikt) { // Go to destiny in y direction
    moveY();
  }

  if (xDoelBereikt && yDoelBereikt) { // Push als doel is bereikt.
    push();
    xDoelBereikt = false;
    yDoelBereikt = false;
  }
  
}

void push() { // Push once
  delay(500);
  Serial.println("PUSH");
  mySerial.print('p');
  mySerial.print('q');

}

void moveX() { // Move to DoelStop in Y direction
  if (xDoelStop != -1) {
    if (xDoelStop < xHuidigeStop) {
      translate('l');
      goLeft();
    }
    else {
      translate('r');
      goRight();
    }
  }

  if (xHuidigeStop == xDoelStop) {
    translate('s');
    xDoelBereikt = true;
    Serial.print("X DOEL BEREIKT");
    Serial.print(", huidige stop: x");
    Serial.println(xHuidigeStop);
    Serial.println();
    incommingCommandChar = 'n';
    xDoelStop = -1;
  }
}

void moveY() { // Move to DoelStop in Y direction.
  if (yDoelStop != -1) {
    if (yDoelStop < yHuidigeStop) {
      translate('d');
      goDown();
    }
    else {
      translate('u');
      goUp();
    }
  }

  if (yHuidigeStop == yDoelStop) {
    translate('s');
    yDoelBereikt = true;
    Serial.print("Y DOEL BEREIKT");
    Serial.print(", huidige stop: x");
    Serial.println(yHuidigeStop);
    Serial.println();
    incommingCommandChar = 'n';
    yDoelStop = -1;
  }
}

void goUp() { // Drive up and check for stops.
  boolean newZwart;
  
  if (analogRead(ySensor) > 180) {
    newZwart = true;
  }
  else {
    newZwart = false;
  }

  if (newZwart != yZwart) {
    if (newZwart == 0) {
      yHuidigeStop++;
    }
    if (newZwart) {
      Serial.println("Zwart");
      translate('u');
    }
    else {
      Serial.print("Wit");
      translate('u');
      Serial.print(" huidige stop: y");
      Serial.println(yHuidigeStop);
    }
  }
  delay(50); // Delay voor kracht omhoog.
  yZwart = newZwart;
}

void goDown() { // Drive down and check for stops.
  boolean newZwart;
  
  if (analogRead(ySensor) > 180) {
    newZwart = true;
  }
  else {
    newZwart = false;
  }

  if (newZwart != yZwart) {
    if (newZwart == 0) {
      yHuidigeStop--;
    }
    if (newZwart) {
      Serial.println("Zwart");
      translate('d');
    }
    else {
      Serial.print("Wit");
      translate('d');
      Serial.print(" huidige stop: y");
      Serial.println(yHuidigeStop);
    }
  }

  yZwart = newZwart;
}

void goRight() { // Drive right and check for stops.
  boolean newZwart;
  
  if (analogRead(xSensor) > 100) {
    newZwart = true;
  }
  else {
    newZwart = false;
  }

  if (newZwart != xZwart) {
    if (newZwart == 0) {
      xHuidigeStop++;
    }
    if (newZwart) {
      Serial.println("Zwart");
      translate('r');
    }
    else {
      Serial.print("Wit");
      translate('r');
      Serial.print(" huidige stop: x");
      Serial.println(xHuidigeStop);
    }
  }

  xZwart = newZwart;
}

void goLeft() { // Drive left and check for stops.
  boolean newZwart;
  
  if (analogRead(xSensor) > 100) {
    newZwart = true;
  }
  else {
    newZwart = false;
  }

  if (newZwart != xZwart) {
    if (newZwart == 0) {
      xHuidigeStop--;
    }
    if (newZwart) {
      Serial.println("Zwart");
      translate('l');
    }
    else {
      Serial.print("Wit");
      translate('l');
      Serial.print(" huidige stop: x");
      Serial.println(xHuidigeStop);
    }
  }

  xZwart = newZwart;
}

void translate(char translation) {
  switch (translation) {
      case 'l': //left
        digitalWrite(xRichting, HIGH);
        analogWrite(xSnelheid, xMaxSnelheid);
      break;
      case 'r': //right
        digitalWrite(xRichting, LOW);
        analogWrite(xSnelheid, xMaxSnelheid);
      break;
      case 'u': // up
        digitalWrite(yRichting, HIGH);
        analogWrite(ySnelheid, 100);
      break;
      case 'd': // down
        digitalWrite(yRichting, LOW);
        analogWrite(ySnelheid, 60);
      break;
      default:
        analogWrite(xSnelheid, 0);
        analogWrite(ySnelheid, 0);
      break;
    }
}


void checkForIncomingCoordinate() { //lees inkomende coordinaten
  // lees het int uit de serial buffer
  int data = Serial.parseInt();

  //splits op in X en Y coordinaten
  xIncomming = ((data/10)%10);
  yIncomming = (data%10);
}
