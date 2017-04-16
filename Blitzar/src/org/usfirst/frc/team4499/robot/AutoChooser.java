package org.usfirst.frc.team4499.robot;

import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.commands.auto.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoChooser {
	public static enum position{NONE,LEFT,LEFTMID,CENTER,RIGHTMID,RIGHT};
	public static CommandGroup getDefense(){
		CommandGroup defense = null;
			
		
		// RED SIDE AUTOS
		
		if(OI.dialTwo.get() && OI.switchTwo.get()){
			// Middle Gear Red shoots 10 balls
			defense = new MiddleGearRed();
    		System.out.println("Middle Gear Red Auto");
    	}
		else if(OI.dialTwo.get() && OI.switchThree.get()){
    		// Right Gear Red Auto 10 balls
    		defense = new RightGearRedAuto();
    		System.out.println("Right Gear Red Auto");
		}
		
		else if(OI.dialTwo.get() && OI.switchOne.get()){
			// Left Gear Red Auto
			defense = new LeftGearRedAuto();
			System.out.println("Left Gear Red Auto");
		
		}
		else if(OI.dialFour.get()){
			// Left Gear Red Auto
			defense = new ShootBlue();
			System.out.println("Left Gear Red Auto");
		
		}
		else if(OI.dialFour.get()){
			// Left Gear Red Auto
			defense = new ShootBlue();
			System.out.println("Left Gear Red Auto");
		}
		///////////////////////////
		
		// BLUE SIDE AUTOS
		else if(OI.dialThree.get() && OI.switchThree.get()){
    		// Right Gear Blue Auto 
    		defense = new RightGearBlueAuto();
    		System.out.println("Right Gear Blue Auto");
		}
		else if(OI.dialThree.get() && OI.switchTwo.get()){
    		// Middle Gear Blue Auto
    		defense = new MiddleGearBlue();
    		System.out.println("Middle Gear Blue Auto");
		}
		else if(OI.dialThree.get() && OI.switchOne.get()){
    		// Left Gear Blue Auto
    		defense = new LeftGearBlue();
    		System.out.println("Left Gear Blue Auto");
		} 
		
		// Drive forward
		
		else if (OI.dialOne.get()) {
			// Drive Forward Auto
			defense = new DriveForwardAuto();
			System.out.println("Drive Forward Auto");
		}
		/*
    	else if(OI.dialTwo.get()){
    		// Portcullis Defense
    		defense = new ShootHighRedSide();
    		System.out.println("SHOOT HIGH RED SIDE SELECTED");
    	}
    	*/
    	else{
    		//NoAuto
    		defense = new NoAuto();
    		System.out.println("NO AUTO CHOSEN");
    	}
		return defense;
	}
	public static position getPosition(){
		if(OI.switchTwo.get()){
			if(OI.switchOne.get()){
				return position.LEFTMID;
			}else if(OI.switchThree.get()){
				return position.RIGHTMID;
			}
			else{
				return position.CENTER;
			}
		}
		else if(OI.switchOne.get()){
			return position.LEFT;
		}
		else if(OI.switchThree.get()){
			return position.RIGHT;
		}
		else{
			return position.NONE;
		}
	}
	public static CommandGroup getAuto(){
		position place = getPosition();
		CommandGroup defense = getDefense();
		if(place != position.NONE){
			//return new Ball_Auto(defense,place);
		}
		return defense;
	}
	}
