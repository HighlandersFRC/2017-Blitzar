package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ControlGearIntakeMP extends Command {
	private int maxvoltage;

    public ControlGearIntakeMP() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//TODO Tune maxvoltage
    	//maxvoltage = 30;
    	//RobotMap.gearIntakeRotate.setStallCurrent(maxvoltage);
    
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RobotMap.gearIntakeRotate.updateStall();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(RobotMap.gearIntakeRotate.checkStall()){
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.gearIntakeRotate.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	RobotMap.gearIntakeRotate.set(0);
    }
}
