package org.usfirst.frc.team4499.robot;

import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.commands.auto.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoChooser {
	public static enum position{NONE,LEFT,LEFTMID,CENTER,RIGHTMID,RIGHT};
	public static CommandGroup getDefense(){
		CommandGroup defense = null;
			
		if(OI.dialTwo.get() && OI.switchTwo.get()){
			// Middle Gear Red shoots 10 balls
			defense = new MiddleGearRed();
    		System.out.println("Middle Gear Red");
    	}
		else if(OI.dialTwo.get() && OI.switchThree.get()){
    		// Right Gear Red Auto 10 balls
    		defense = new RightGearRedAuto();
    		System.out.println("Right Gear Red Auto");
		}
		
		else if(OI.switchOne.get() && OI.dialTwo.get()){
			// Right Gear Blue Auto 
			defense = new LeftGearRedAuto();
			System.out.println("Left Gear Red Auto");
		}	
		else if(OI.switchThree.get() && OI.dialThree.get()){
    		// Right Gear Blue Auto 
    		defense = new RightGearBlueAuto();
    		System.out.println("Right Gear Blue Auto");
		}
		else if(OI.switchThree.get() && OI.dialThree.get()){
    		// Right Gear Blue Auto 
    		defense = new RightGearBlueAuto();
    		System.out.println("Right Gear Blue Auto");
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
