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
	private double forwardSoftLimit = 0;
	private double reverseSoftLimit = -0.6;
	private double targetPosition = forwardSoftLimit;
	private double previousCurrentDraw;
	private double lastCurrentDraw;
	private boolean positionMode = false;
	private double currentReverseLimit = reverseSoftLimit;
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
    	//System.out.println("INIT GEAR CONTROL");
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
    	//System.out.println("Execute GI");
    	if (positionMode) {
    		
    		RobotMap.gearIntakeRotate.set(getRotatePosition());
    		
    	} else {
    		RobotMap.gearIntakeRotate.set(getRotatePower());
    	}
    	RobotMap.gearIntakeRoller.set(getRollerPower());
    	//System.out.println("current reverse limit " + currentReverseLimit);
    	/*if (positionMode) {
    		System.out.println("Target position: " + targetPosition + "   Position " + RobotMap.gearIntakeRotate.getPosition()); 
    	}*/
    }

	private double getRotatePower() {
		if (OI.zeroGearIntakeRotate.get()) {
			//System.out.println("holding x");
			RobotMap.gearIntakeRotate.enableForwardSoftLimit(false);
			RobotMap.gearIntakeRotate.changeControlMode(TalonControlMode.PercentVbus);
			RobotMap.gearIntakeRotate.set(0.2);
			if ((RobotMap.gearIntakeRotate.getOutputCurrent() >= 1.625) && (lastCurrentDraw >= 1.625) && (previousCurrentDraw >= 1.625)) {
				RobotMap.gearIntakeRotate.setPosition(0);
				//System.out.println("ZEROED ENCODER");
			}
			previousCurrentDraw = lastCurrentDraw;
			lastCurrentDraw = RobotMap.gearIntakeRotate.getOutputCurrent();
			
		} else {
			RobotMap.gearIntakeRotate.enableForwardSoftLimit(true);
			previousCurrentDraw = 0;
			lastCurrentDraw = 0;
			RobotMap.gearIntakeRotate.changeControlMode(TalonControlMode.Position);
			if (OI.joystickTwo.getPOV() == 0) {
	    		// Move intake up
				System.out.println("Gear Down");
	    		rotateSetPower = -0.4;
	    		
	    	} else if (OI.joystickTwo.getPOV() == 180) {
	    		// Move intake down
	    		System.out.println("Gear Up");
	    		rotateSetPower = 0.2;
	    		
	    	} else {
	    		rotateSetPower = 0;
	    	}
		}
		return rotateSetPower;
	}

	
	private double getRotatePosition() {
		if (OI.zeroGearIntakeRotate.get()) {
			targetPosition = 0.2;
			//System.out.println("holding x");
			RobotMap.gearIntakeRotate.enableForwardSoftLimit(false);
			RobotMap.gearIntakeRotate.changeControlMode(TalonControlMode.PercentVbus);
			RobotMap.gearIntakeRotate.set(0.2);
			if ((RobotMap.gearIntakeRotate.getOutputCurrent() >= 1.625) && (lastCurrentDraw >= 1.625) && (previousCurrentDraw >= 1.625)) {
				RobotMap.gearIntakeRotate.setPosition(0);
				//System.out.println("ZEROED ENCODER");
				
				currentReverseLimit = -0.65;
				reverseSoftLimit = -0.65;
				RobotMap.gearIntakeRotate.setReverseSoftLimit(reverseSoftLimit);
			}
			previousCurrentDraw = lastCurrentDraw;
			lastCurrentDraw = RobotMap.gearIntakeRotate.getOutputCurrent();
			
		} else {
			RobotMap.gearIntakeRotate.enableForwardSoftLimit(true);
			RobotMap.gearIntakeRotate.enableReverseSoftLimit(true);
			previousCurrentDraw = 0;
			lastCurrentDraw = 0;
		RobotMap.gearIntakeRotate.changeControlMode(TalonControlMode.Position);
			
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
		}
		return targetPosition;
	}
	
	private double getRollerPower() {
		if (OI.joystickTwo.getRawAxis(2) > 0.5) {	
    		// Left trigger = eject gear
    		rollerSetPower = .5;
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
