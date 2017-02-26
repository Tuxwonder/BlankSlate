#include <Wire.h>
#include <Servo.h>
#include <Adafruit_MotorShield.h>
#include "utility/Adafruit_MS_PWMServoDriver.h"

String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete

Adafruit_MotorShield ms1 = Adafruit_MotorShield(0x60);
Adafruit_StepperMotor *stepper1 = ms1.getStepper(200, 1);
Adafruit_StepperMotor *stepper2 = ms1.getStepper(200, 2);

int LED = 13;

void setup() {
  Serial.begin(115200);

  ms1.begin();

  stepper1->setSpeed(30);
  stepper2->setSpeed(30);

  pinMode(LED, OUTPUT);
  pinMode(12, OUTPUT);
  digitalWrite(12, HIGH);

  stepper2->step(50, FORWARD, DOUBLE);
}

void loop() {
  // when a newline arrives:
  if (stringComplete) {
    Serial.println("You printed a line!");
    stepper1->step(50, FORWARD, DOUBLE);
    stepper2->step(50, BACKWARD, DOUBLE);
    digitalWrite(LED, HIGH);
    delay(1000);

    // clear the string:
    inputString = "";
    stringComplete = false;
  }
  else {
    digitalWrite(LED, LOW);
  }
}

void serialEvent() {
  while (Serial.available() && !stringComplete) {
    char inChar = (char)Serial.read();  // get the new byte:
    inputString += inChar;        // add it to the inputString:
    if (inChar == '\n' || inChar == '-') {        // if the incoming character is a newline, set a flag
      stringComplete = true;
    }
    Serial.println("Serial Available");
  }
}

