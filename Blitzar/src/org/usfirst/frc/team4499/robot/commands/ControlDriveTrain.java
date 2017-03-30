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

	private static final int CURRENT_LIMIT = 40;
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
    		System.out.println("No current protection");
    		setMotorsNoCurrentProtection();	
    	} else {  		
    		setMotorsCurrentProtection();
    		
    	}
    		
    }
    
    private void setMotorsCurrentProtection() {
    	double minCurrentPercentage = 1;
    	if (calcCurrentPercent(leftMotorOne) < minCurrentPercentage) {
    		minCurrentPercentage = calcCurrentPercent(leftMotorOne);
    	}
    	if (calcCurrentPercent(leftMotorTwo) < minCurrentPercentage) {
    		minCurrentPercentage = calcCurrentPercent(leftMotorTwo);
    	}
    	if (calcCurrentPercent(rightMotorOne) < minCurrentPercentage) {
    		minCurrentPercentage = calcCurrentPercent(rightMotorOne);
    	}
    	if (calcCurrentPercent(rightMotorTwo) < minCurrentPercentage) {
    		minCurrentPercentage = calcCurrentPercent(rightMotorTwo);
    	}
    	//System.out.println("Current protection scaled to " + minCurrentPercentage);
    	
    	limitMotorPower(leftMotorOne, OI.joystickOne.getRawAxis(1), minCurrentPercentage);
    	limitMotorPower(leftMotorTwo, OI.joystickOne.getRawAxis(1), minCurrentPercentage);
    	limitMotorPower(rightMotorOne, -OI.joystickOne.getRawAxis(5), minCurrentPercentage);
    	limitMotorPower(rightMotorTwo, -OI.joystickOne.getRawAxis(5), minCurrentPercentage);
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
    
	private void limitMotorPower(CANTalon driveMotor, double setPower, double currentScale) {
		if (Math.abs(setPower) < 0.2) {
			setPower = 0;
		}
		driveMotor.set(setPower * currentScale);
	}

	private double calcCurrentPercent(CANTalon driveMotor) {
		if (driveMotor.getOutputCurrent() > CURRENT_LIMIT) {
			return CURRENT_LIMIT / driveMotor.getOutputCurrent();
		}
		return 1;
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
