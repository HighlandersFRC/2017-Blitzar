package org.usfirst.frc.team4499.robot.commands;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetTalonBrakeCoast extends Command {

	private CANTalon _talon;
	private boolean brakeMode;
	
    public SetTalonBrakeCoast(CANTalon talon, boolean brake) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	_talon = talon;
    	brakeMode = brake;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(brakeMode) {
    		_talon.enableBrakeMode(brakeMode);
    				
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
