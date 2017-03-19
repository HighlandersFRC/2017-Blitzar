package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public class ControlDriveTrain extends Command {

	private static final int CURRENT_LIMIT = 30;
	private static final int MAX_CURRENT_OFF_COUNT = 3;
	private CANTalon rightMotorOne;
	private CANTalon rightMotorTwo;
	private CANTalon leftMotorOne;
	private CANTalon leftMotorTwo;
	private int currentOffCount;
	
    public ControlDriveTrain(CANTalon l1, CANTalon l2, CANTalon r1, CANTalon r2) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.driveTrain);
    	
    	rightMotorOne = r1;
    	rightMotorTwo = r2;
    	leftMotorOne = l1;
    	leftMotorTwo = l2;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.leftMotorOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.leftMotorTwo.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.rightMotorOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.rightMotorTwo.changeControlMode(TalonControlMode.PercentVbus);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    
    	if (OI.climbPistonOut.get()){
    		//System.out.println("Brownout protection enabled");
    		
    	checkCurrent(leftMotorOne, OI.joystickOne.getRawAxis(1));
    	checkCurrent(leftMotorTwo, OI.joystickOne.getRawAxis(1));
    	checkCurrent(rightMotorOne, -OI.joystickOne.getRawAxis(5));
    	checkCurrent(rightMotorTwo, -OI.joystickOne.getRawAxis(5));
    	
    	} else {
    		//System.out.println("Brownout protection disabled");
    	setMotorsNoCurrentProtection();
    	}
    		
    }

	private void setMotorsNoCurrentProtection() {
		if (Math.abs(OI.joystickOne.getRawAxis(1)) > 0.2) {
    		
    		leftMotorOne.set(OI.joystickOne.getRawAxis(1)); // Up on joystick returns lower
    		leftMotorTwo.set(OI.joystickOne.getRawAxis(1)); // Negative -> Correct direction
    		
    	} else {
    		leftMotorOne.set(0); 
    		leftMotorTwo.set(0);
    	}
    		
    	if (Math.abs(OI.joystickOne.getRawAxis(5)) > 0.2) {
    		rightMotorOne.set(-OI.joystickOne.getRawAxis(5));
    		rightMotorTwo.set(-OI.joystickOne.getRawAxis(5));
    		
    	} else {
    		rightMotorOne.set(0); 
    		rightMotorTwo.set(0);
    	}
	}
    
	private void checkCurrent(CANTalon driveMotor, double setPower) {
		if (Math.abs(setPower) < 0.2) {
			setPower = 0;
		} else {
			if (currentOffCount > MAX_CURRENT_OFF_COUNT) {
				currentOffCount = 0;
			}
			if (shouldTurnMotorOff(driveMotor)) {
				driveMotor.set(0);
				currentOffCount++;
			} else {
				driveMotor.set(setPower);
				currentOffCount = 0;
			}
		}
	}

	private boolean shouldTurnMotorOff(CANTalon driveMotor) {
		return driveMotor.getOutputCurrent() > CURRENT_LIMIT || currentOffCount < MAX_CURRENT_OFF_COUNT;
	}

    
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	leftMotorOne.set(0);
    	leftMotorTwo.set(0);
    	rightMotorOne.set(0);
    	rightMotorTwo.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	leftMotorOne.set(0);
    	leftMotorTwo.set(0);
    	rightMotorOne.set(0);
    	rightMotorTwo.set(0);
    }
}
