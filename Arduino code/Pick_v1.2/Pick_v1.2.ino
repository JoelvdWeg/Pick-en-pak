int xSnelheid = 5;
int xRichting = 4;
int xMaxSnelheid = 70;
int xSensor = A0;

boolean xDoelBereikt = false;
boolean xZwart = false;
int xDoelStop = -1; // -1 betekent not set.
int xHuidigeStop = 0;


int ySnelheid = 6;
int yRichting = 7;
int ySensor = A1;

boolean yDoelBereikt = false;
boolean yZwart = false;
int yDoelStop = -1; // -1 betekent not set.
int yHuidigeStop = 0;


char incommingChar = 'n'; // n betekent not set.
String incommingString; 

void setup() {
  Serial.begin(9600);
  
  pinMode(xRichting, OUTPUT);

  Serial.println("--- klaar ---");
}

void loop() {
  if (Serial.available() > 0) {

    
    
    incommingChar = Serial.read();
    
    if (incommingChar == 'l' || incommingChar == 'r' || incommingChar == 's' || incommingChar == 'u' || incommingChar == 'd') { // handmatig besturen
      xDoelStop = -1;
      translate(incommingChar);
      Serial.print("HANDMATIG, invoer: ");
      Serial.println(incommingChar);
      incommingChar = 'n';
    }
    else {
      checkIncomingData();
    }

    delay(50);
  }
  
  if (!xDoelBereikt) {
    moveX();
  }
  else if (!yDoelBereikt) {
    //moveY();
  }
  
}

void moveX() {
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
    incommingChar = 'n';
    xDoelStop = -1;
  }
}

void moveY() {
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
    incommingChar = 'n';
    yDoelStop = -1;
  }
}


void goUp() {
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

  yZwart = newZwart;
}

void goDown() {
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

void goRight() {
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

void goLeft() {
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
      case 'l':
        digitalWrite(xRichting, HIGH);
        analogWrite(xSnelheid, xMaxSnelheid);
      break;
      case 'r':
        digitalWrite(xRichting, LOW);
        analogWrite(xSnelheid, xMaxSnelheid);
      break;
      case 'u':
        digitalWrite(yRichting, HIGH);
        analogWrite(ySnelheid, 100);
      break;
      case 'd':
        digitalWrite(yRichting, LOW);
        analogWrite(ySnelheid, 40);
      break;
      default:
        analogWrite(xSnelheid, 0);
        analogWrite(ySnelheid, 0);
      break;
    }
}


void checkIncomingData() { //lees inkomende coordinaten
  // lees het int uit de serial buffer
  int data = Serial.parseInt();

  //splits op in X en Y coordinaten
  xDoelStop = ((data/10)%10);
  xDoelBereikt = false;
  yDoelStop = (data%10);
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
