package org.usfirst.frc.team4499.robot;

import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.commands.auto.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoChooser {
	public static enum position{NONE,LEFT,LEFTMID,CENTER,RIGHTMID,RIGHT};
	public static CommandGroup getDefense(){
		CommandGroup defense = null;
		
		if(OI.dialOne.get()){
			// ChevalDeFrise Defense
			defense = new DriveForwardAuto();
    		System.out.println("DRIVE FORWARD SELECTED");
    	}
    	else if(OI.dialTwo.get()){
    		// Portcullis Defense
    		defense = new ShootHighRedSide();
    		System.out.println("SHOOT HIGH RED SIDE SELECTED");
    	}
    	else if(OI.dialThree.get()){
    		// D_Defense
    		defense = new ShootHighBlueSide();
    		System.out.println("SHOOT HIGH BLUE SIDE SELECTED");
    	}
    	else if(OI.dialFour.get()){
    		// B_Defense
    		defense = new NoAuto();
    		System.out.println("NO AUTO SELECTED");
    	}
    	else if(OI.dialFive.get()){
    		//Low Bar
    		defense = new ShootHighGear();
    		System.out.println("SHOOT HIGH GEAR AUTO SELECTED");
    	}
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
