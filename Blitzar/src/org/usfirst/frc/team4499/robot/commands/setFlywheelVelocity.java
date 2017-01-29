package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4499.robot.*;
import com.ctre.CANTalon;



/**
 *
 */
public class setFlywheelVelocity extends Command {

	int setVelocity;
	
    public setFlywheelVelocity(int desiredVelocity) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.flywheel);
    	setVelocity = desiredVelocity;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.flywheel.changeControlMode(CANTalon.TalonControlMode.Speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RobotMap.flywheel.set(setVelocity);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return (Math.abs(setVelocity - RobotMap.flywheel.getSpeed()) < 10);
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("setFlywheelVelocity interrupted");
    }
}