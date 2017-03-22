package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ControlGearIntake extends Command {

	private double rotateSetPower;
	private double rollerSetPower;
	private double forwardSoftLimit = -0.03;
	private double reverseSoftLimit = -0.6;
	private double targetPosition = forwardSoftLimit;
	private boolean positionMode = false;
	private boolean hasGear = false;
	
    public ControlGearIntake(boolean positionMode) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearIntake);
    	
     	this.positionMode = positionMode;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Choose control mode
    	if (positionMode && 
    			RobotMap.gearIntakeRotate.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative)
    			== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent)
    	{
    		RobotMap.gearIntakeRotate.changeControlMode(TalonControlMode.Position);
    		RobotMap.gearIntakeRotate.setPID(1.05, 0, 2);
    		RobotMap.gearIntakeRotate.setF(0.08);
    		RobotMap.gearIntakeRotate.setIZone(0);
    	} 
    	else 
    	{
    		RobotMap.gearIntakeRotate.changeControlMode(TalonControlMode.PercentVbus);
    	}
    	RobotMap.gearIntakeRoller.changeControlMode(TalonControlMode.PercentVbus);
    	
    	// Set soft limits
    	RobotMap.gearIntakeRotate.setForwardSoftLimit(forwardSoftLimit);
    	RobotMap.gearIntakeRotate.setReverseSoftLimit(reverseSoftLimit);
    	RobotMap.gearIntakeRotate.enableForwardSoftLimit(true);
    	RobotMap.gearIntakeRotate.enableReverseSoftLimit(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (positionMode) {
    		RobotMap.gearIntakeRotate.set(getRotatePosition());
    	} else {
    		RobotMap.gearIntakeRotate.set(getRotatePower());
    	}
    	RobotMap.gearIntakeRoller.set(getRollerPower());
    	
    	if (positionMode) {
    		System.out.println("Target position: " + targetPosition + "   Position " + RobotMap.gearIntakeRotate.getPosition()); 
    	}
    }

	private double getRotatePower() {
		if (OI.joystickTwo.getPOV() == 0) {
    		// Move intake up
    		rotateSetPower = -0.4;
    	} else if (OI.joystickTwo.getPOV() == 180) {
    		// Move intake down
    		rotateSetPower = 0.4;
    		
    	} else {
    		rotateSetPower = 0;
    	}
		
		return rotateSetPower;
	}

	
	private double getRotatePosition() {
		if (OI.joystickTwo.getPOV() == 0) {
			// Move intake up
			targetPosition -= 0.05;
		} else if (OI.joystickTwo.getPOV() == 180) {
			// Move intake down
			targetPosition += 0.05;
		}
		
		// Constrain targetPosition
		if (targetPosition > forwardSoftLimit) targetPosition = forwardSoftLimit;
		if (targetPosition < reverseSoftLimit) targetPosition = reverseSoftLimit;
		
		return targetPosition;
	}
	
	private double getRollerPower() {
		if (OI.joystickTwo.getRawAxis(2) > 0.5) {	
    		// Left trigger = eject gear
    		rollerSetPower = 1;
    		// No longer has gear
    		hasGear = false;
    	} else if (OI.joystickTwo.getRawAxis(3) > 0.5) {
    		// Right trigger = intake gear
    		rollerSetPower = -0.5;
    		// Just obtained a gear
    		hasGear = true;
    	} else if (hasGear){
    		// If robot has gear, hold on to it
    		rollerSetPower = -0.12;
    	} else {
    		// If the robot doesn't have the gear, don't power the intake rollers
    		rollerSetPower = 0;
    	}
		
		return rollerSetPower;
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
    	RobotMap.gearIntakeRoller.set(0);
    	RobotMap.gearIntakeRotate.set(0);
    }
}
