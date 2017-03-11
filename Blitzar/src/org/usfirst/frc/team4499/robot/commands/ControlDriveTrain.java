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

	private CANTalon rightMotorOne;
	private CANTalon rightMotorTwo;
	private CANTalon leftMotorOne;
	private CANTalon leftMotorTwo;
	
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
    	if (Math.abs(OI.joystickOne.getRawAxis(1)) > 0.2) {
    		if (leftMotorOne.getOutputCurrent() > 10) {
    			leftMotorOne.set(0);
    		} else {
    		leftMotorOne.set(OI.joystickOne.getRawAxis(1)); // Up on joystick returns lower
    		}
    		if (leftMotorTwo.getOutputCurrent() > 10) {
    			leftMotorTwo.set(0);
    		} else {
    		leftMotorTwo.set(OI.joystickOne.getRawAxis(1)); // Negative -> Correct direction
    		}
    	} else {
    		leftMotorOne.set(0); 
    		leftMotorTwo.set(0);
    	}
    		
    	if (Math.abs(OI.joystickOne.getRawAxis(5)) > 0.2) {
    		if (rightMotorOne.getOutputCurrent() > 10) {
    			rightMotorOne.set(0);
    		} else {
    		rightMotorOne.set(-OI.joystickOne.getRawAxis(5));
    		}
    		
    		if (rightMotorTwo.getOutputCurrent() > 10) {
    			rightMotorTwo.set(0);
    		} else {
    		rightMotorTwo.set(-OI.joystickOne.getRawAxis(5));
    		}
    		
    	} else {
    		rightMotorOne.set(0); 
    		rightMotorTwo.set(0);
    	}
    	
    	} else {
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
    	
    	/*if (rightMotorOne.getOutputCurrent() > 20) {
			rightMotorOne.set(0);
			System.out.println("set right1 to 0");
		}
    	if (rightMotorTwo.getOutputCurrent() > 20) {
			rightMotorTwo.set(0);
			System.out.println("set right2 to 0");
		}
    	if (leftMotorOne.getOutputCurrent() > 20) {
			leftMotorOne.set(0);
			System.out.println("set left1 to 0");
		}
    	if (leftMotorTwo.getOutputCurrent() > 20) {
			leftMotorTwo.set(0);
			System.out.println("set left2 to 0");
		}
    	*/
    	
    	
    	
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
