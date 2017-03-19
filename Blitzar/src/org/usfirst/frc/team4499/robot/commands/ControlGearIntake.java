package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ControlGearIntake extends Command {

	private double rotateSetPower;
	private double rollerSetPower;
	
    public ControlGearIntake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.gearIntakeRoller.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.gearIntakeRotate.changeControlMode(TalonControlMode.PercentVbus);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RobotMap.gearIntakeRoller.set(getRollerPower());
    	RobotMap.gearIntakeRotate.set(getRotatePower());
    }

	private double getRotatePower() {
		if (OI.joystickTwo.getPOV() == 0) {
    		// Move intake up
    		rotateSetPower = 0.4;
    	} else if (OI.joystickTwo.getPOV() == 180) {
    		// Move intake down
    		rotateSetPower = -0.4;
    	} else {
    		rotateSetPower = 0;
    	}
		
		return rotateSetPower;
	}

	private double getRollerPower() {
		if (OI.joystickTwo.getRawAxis(2) > 0.5) {
    		// Left trigger = eject gear
    		rollerSetPower = -1;
    	} else if (OI.joystickTwo.getRawAxis(3) > 0.5) {
    		// Right trigger = intake gear
    		rollerSetPower = 1;
    	} else {
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
