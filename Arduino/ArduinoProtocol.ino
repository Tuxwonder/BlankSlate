
#include <Wire.h>
#include <Servo.h>
#include <Adafruit_MotorShield.h>
#include "utility/Adafruit_MS_PWMServoDriver.h"

String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete

Adafruit_MotorShield ms1 = Adafruit_MotorShield(0x60);
Adafruit_MotorShield ms2 = Adafruit_MotorShield(0x61);

Adafruit_StepperMotor *stepper1 = ms1.getStepper(200, 1);
Adafruit_StepperMotor *stepper2 = ms1.getStepper(200, 2);
Adafruit_StepperMotor *stepper3 = ms2.getStepper(200, 1);

Servo servo1;

boolean autoStepper[] = {false, false, false, false};

void setup() {
  Serial.begin(9600);
  servo1.attach(9);

  ms1.begin();
  ms2.begin();

  stepper1->setSpeed(30);
  stepper2->setSpeed(30);
  stepper3->setSpeed(30);

  stepper1->release();
  stepper2->release();
  stepper3->release();
}

void loop() {
  // when a newline arrives:
  if (stringComplete) {
    boolean msgRecognized = true;
    if (inputString.startsWith("jj//")) {
      if (inputString.substring(4, 6) == "sm") { //It's a motor command
        
        int motor = inputString.substring(6,7).toInt();
        
        if (inputString.substring(8, 11) == "rpm") {
          int rpm = inputString.substring(12).toInt();
          switch (motor) {
            case 1:
              stepper1->setSpeed(rpm);
            case 2:
              stepper2->setSpeed(rpm);
            case 3:
              stepper3->setSpeed(rpm);
          }
          
        } else if (inputString.substring(8,10) == "on") {
          autoStepper[motor] = true;
          
        } else if (inputString.substring(8,11) == "off") {
          autoStepper[motor] = false;
          switch (motor) {
            case 1:
              stepper1->release();
            case 2:
              stepper2->release();
            case 3:
              stepper3->release();
          }
          
        } else if(inputString.substring(8,12) == "step") {
          #define dir FORWARD
          
        }
      }
      if (inputString.substring(4,8) == "help") {
        returnHelp();
      }
    }
    else {
      msgRecognized = false;
    }

    Serial.print(inputString);

    if (!msgRecognized) { // this sketch doesn't know other messages in this case command is ko (not ok)
      Serial.print("   (bad command)");
      Serial.write(255);
      Serial.flush();
    }

    // clear the string:
    inputString = "";
    stringComplete = false;
  }



  if(autoStepper[1] == true) {
    stepper1->step(1, FORWARD, DOUBLE);
  }
  if(autoStepper[2] == true) {
    stepper2->step(1, FORWARD, DOUBLE);
  }
  if(autoStepper[3] == true) {
    stepper3->step(1, FORWARD, DOUBLE);
  }
}


void serialEvent() {
  while (Serial.available() && !stringComplete) {
    char inChar = (char)Serial.read();	// get the new byte:
    inputString += inChar;				// add it to the inputString:
    if (inChar == '\n' || inChar == '-') {				// if the incoming character is a newline, set a flag
      stringComplete = true;
    }
    Serial.println("Serial Available");
  }
}

void returnHelp() {
  Serial.write("--- REFERENCE TABLE ---");
  Serial.write("\n");
  Serial.write("--- --------------- ---");
  Serial.write("\n");
}
