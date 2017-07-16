package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetGearIntake extends Command {

	private double rotateSetPower;
	private double rollerSetPower;
	private double forwardSoftLimit = 0;
	private double reverseSoftLimit = -0.6;
	private double targetPosition = forwardSoftLimit;
	private double targetRollerPower;
	private boolean positionMode = false;
	private boolean hasGear = false;
	
    public SetGearIntake(double targetRotation, double targetRollerPower) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);requires(Robot.gearIntake);
    	if (targetRotation > forwardSoftLimit) {
    		targetRotation = forwardSoftLimit;
    	}
    	if (targetRotation < reverseSoftLimit) {
    		targetRotation = reverseSoftLimit;
    	}
    	
    	this.targetPosition = targetRotation;
    	this.targetRollerPower = targetRollerPower;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	// Set soft limits
    	RobotMap.gearIntakeRotate.setForwardSoftLimit(forwardSoftLimit);
    	RobotMap.gearIntakeRotate.setReverseSoftLimit(reverseSoftLimit);
    	RobotMap.gearIntakeRotate.enableForwardSoftLimit(true);
    	RobotMap.gearIntakeRotate.enableReverseSoftLimit(true);
    	
    	if (positionMode && 
    			RobotMap.gearIntakeRotate.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative)
    			== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent)
    	{
    		RobotMap.gearIntakeRotate.changeControlMode(TalonControlMode.Position);
    		RobotMap.gearIntakeRotate.setPID(1.05, 0, 2);
    		RobotMap.gearIntakeRotate.setF(0.08);
    		RobotMap.gearIntakeRotate.setIZone(0);
    	} 
    	RobotMap.gearIntakeRotate.set(targetPosition);
    	RobotMap.gearIntakeRoller.set(targetRollerPower);
    	
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
