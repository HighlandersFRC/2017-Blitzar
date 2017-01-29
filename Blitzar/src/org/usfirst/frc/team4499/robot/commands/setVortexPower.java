package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class setVortexPower extends Command {

	float setPower;
	
    public setVortexPower(float vortexPower) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setPower = vortexPower;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.vortexMotorOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.vortexMotorTwo.changeControlMode(TalonControlMode.PercentVbus);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RobotMap.vortexMotorOne.set(setPower);
    	RobotMap.vortexMotorTwo.set(setPower);
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
    	System.out.println("setVortexPower interrupted");
    }
}
