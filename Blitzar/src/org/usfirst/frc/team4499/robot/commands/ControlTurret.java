package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ControlTurret extends Command {

	private float setPower;
	
    public ControlTurret() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.turret);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (OI.turretPanLeft.get()) {
    		setPower = (float) -0.1; // Left = negative
		} else if (OI.turretPanRight.get()) {
			setPower = (float) +0.1; // Right = positive 
		} else {
			setPower = 0;
		}
    	
    	if (OI.turretPanLeft.get() && OI.turretPanRight.get()) {
    		setPower = 0;
    	}
    	
    	RobotMap.turretMotor.set(setPower);
    	
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