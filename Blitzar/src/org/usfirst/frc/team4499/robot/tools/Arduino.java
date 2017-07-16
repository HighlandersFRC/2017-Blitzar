package org.usfirst.frc.team4499.robot.tools;

import edu.wpi.first.wpilibj.DigitalOutput;

public class Arduino {
	
	public static DigitalOutput pinOne = new DigitalOutput(1);
	public static DigitalOutput pinTwo = new DigitalOutput(2);
	public static DigitalOutput pinThree = new DigitalOutput(3);
	
	//0 = disabled
	//1 = autonomous
	//2 = enabled
	// write an int in binary coding to the arudunio's input pins
	public static void write(int value){
		if(value == 0){
			pinOne.set(false);
			pinTwo.set(false);
			pinThree.set(false);
			
		}
		else if(value == 1){
			pinOne.set(true);
			pinTwo.set(false);
			pinThree.set(false);
			
		}
		else if(value == 2){
			pinOne.set(false);
			pinTwo.set(true);
			pinThree.set(false);
					
		}
		else if(value == 3){
			pinOne.set(true);
			pinTwo.set(true);
			pinThree.set(false);
			
		}
		else if(value == 4){
			pinOne.set(false);
			pinTwo.set(false);
			pinThree.set(true);
			
		}
		else if(value == 5){
			pinOne.set(true);
			pinTwo.set(false);
			pinThree.set(true);
			
		}
		else if(value == 6){
			pinOne.set(false);
			pinTwo.set(true);
			pinThree.set(true);
			
		}
		else if(value == 7){
			pinOne.set(true);
			pinTwo.set(true);
			pinThree.set(true);
			
		}
		
	}
}
