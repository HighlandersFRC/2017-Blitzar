package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ControlReceiver extends Command {

	private float receiverPower;
	
    public ControlReceiver() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.receiver);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	receiverPower = Robot.receiverPower;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if (OI.receiverSpeedIncrease.get()) {
    		// Increase speed
    		receiverPower -= 0.01; // Negative is the correct direction, speed increase -> negative
    	}
    	if (OI.receiverSpeedDecrease.get()) {
    		// Decrease speed
			receiverPower += 0.01; // Negative is the correct direction, speed decrease -> positive
		}
    	
    	// Keep motor within 0 throttle and full speed in the correct direction
		if (receiverPower < -1) {
			receiverPower = -1;
			System.out.println("Setting flywheel power to -1 because it was less than -1");
		}
		if (receiverPower > 0) {
			receiverPower = 0;
			System.out.println("Setting flywheel power to 0 because it was greater than 0");
		}
		RobotMap.receiverLeft.set(receiverPower);
		RobotMap.receiverRight.set(receiverPower);
		
		// Prints twice so that one can be a progress bar, and the other can be a raw value
		SmartDashboard.putNumber("Receiver power", -receiverPower);
		SmartDashboard.putNumber( "Receiver power value", -receiverPower);
		//System.out.println("receiverPower " + receiverPower);
		Robot.receiverPower = receiverPower;
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (OI.receiverSpeedIncrease.get() && !OI.receiverSpeedDecrease.get());
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
