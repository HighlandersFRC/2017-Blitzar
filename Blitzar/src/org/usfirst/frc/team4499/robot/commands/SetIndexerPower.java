package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetIndexerPower extends Command {

	private double setPower;
	
    public SetIndexerPower(float power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setPower = power;
    	
    }
    
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.receiverRight.changeControlMode(TalonControlMode.PercentVbus);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//RobotMap.indexer.set(setPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//RobotMap.indexer.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
