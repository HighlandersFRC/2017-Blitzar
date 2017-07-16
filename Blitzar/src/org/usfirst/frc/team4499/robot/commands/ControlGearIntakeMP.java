package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;


import com.ctre.CANTalon;

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

    	//TODO Add soft limits
    	RobotMap.gearIntakeRoller.changeControlMode(CANTalon.TalonControlMode.MotionMagic);
    	RobotMap.gearIntakeRoller.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    	RobotMap.gearIntakeRoller.setEncPosition(0);
    	//RobotMap.gearIntakeRoller.setForwardSoftLimit();
    	//RobotMap.gearIntakeRoller.setReverseSoftLimit();
    	RobotMap.gearIntakeRoller.setMotionMagicAcceleration(500);
    	RobotMap.gearIntakeRoller.setMotionMagicCruiseVelocity(500);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RobotMap.gearIntakeRotate.updateStall();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

    	if(RobotMap.gearIntakeRoller.getEncPosition() == 0){

    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

    	RobotMap.gearIntakeRoller.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	RobotMap.gearIntakeRoller.set(0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	RobotMap.gearIntakeRotate.set(0);
    }
}
