int coord; // String voor inkomende data van de HMI-applicatie

//huidige locatie van pick robot
int currentX = 0;
int currentY = 4;

int x;
int y;

void setup() {
  Serial.begin(9600);
}

void loop() {

  checkIncomingData();
  
}


void checkIncomingData() { //lees inkomende coordinaten

  if (Serial.available()) {
    // lees het int uit de serial buffer
    int data = Serial.parseInt();

    //splits op in X en Y coordinaten
    x =((data/10)%10);
    y = (data%10);
  }
  
}
