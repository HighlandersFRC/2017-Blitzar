package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetDriveTrainCurrent extends Command {

	private double currentSetValue;
	
    public SetDriveTrainCurrent(double currentSetValue) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	this.currentSetValue = currentSetValue;
    }

    // Called just before this Command runs the first time
    protected void initialize(double currentSetValue) {
    	RobotMap.rightMotorOne.changeControlMode(TalonControlMode.Current);
    	RobotMap.rightMotorTwo.changeControlMode(TalonControlMode.Current);
    	RobotMap.leftMotorOne.changeControlMode(TalonControlMode.Current);
    	RobotMap.leftMotorTwo.changeControlMode(TalonControlMode.Current);
    	
    	RobotMap.rightMotorOne.set(-currentSetValue);
    	RobotMap.rightMotorTwo.set(-currentSetValue);
    	RobotMap.leftMotorOne.set(currentSetValue);
    	RobotMap.leftMotorTwo.set(currentSetValue);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("set drive current to " + currentSetValue);
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
