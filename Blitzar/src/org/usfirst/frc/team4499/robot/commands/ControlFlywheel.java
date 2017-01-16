package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ControlFlywheel extends Command {

	private float flyWheelPower = 0;
	
    public ControlFlywheel() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.flywheel);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	// Increase/Decrease flywheel power
    	if (OI.flyWheelSpeedIncrease.get()) {
    		// Increase speed
    		flyWheelPower -= 0.01; // Negative is the correct direction, speed increase -> negative
    	}
    	if (OI.flyWheelSpeedDecrease.get()) {
    		// Decrease speed
			flyWheelPower += 0.01; // Negative is the correct direction, speed decrease -> positive
		}
    	
    	// Keep motor within 0 throttle and full speed in the correct direction
		if (flyWheelPower < -1) {
			flyWheelPower = -1;
		}
		if (flyWheelPower > 0) {
			flyWheelPower = 0;
		}
		RobotMap.flywheel.set(flyWheelPower);
		
		// Prints twice so that one can be a progress bar, and the other can be a raw value
		SmartDashboard.putNumber("Flywheel power", -flyWheelPower);
		SmartDashboard.putNumber("Flywheel Power value", -flyWheelPower);
		System.out.println("flyWheelPower " + flyWheelPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (!OI.flyWheelSpeedDecrease.get() && !OI.flyWheelSpeedIncrease.get());
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
