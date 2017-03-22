package org.usfirst.frc.team4499.robot.commands;
import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class HoldGear extends Command {
    public HoldGear() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("HoldGear STARTING!!");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(RobotMap.gearIntakeRoller.getOutputCurrent() <= 5){
    		RobotMap.gearIntakeRoller.set(-0.05);
    	}
    	else{
    		RobotMap.gearIntakeRoller.set(0);
    	}
    		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    //	if(OI.joystickOne.getPOV() == 180){
    		//return true;
    	//
    			
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.gearIntakeRoller.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	RobotMap.gearIntakeRoller.set(0);
    }
}


