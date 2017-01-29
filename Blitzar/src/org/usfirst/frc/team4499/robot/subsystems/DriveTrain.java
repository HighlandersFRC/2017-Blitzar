package org.usfirst.frc.team4499.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.*;
import com.ctre.CANTalon;

/**
 *
 */


public class DriveTrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	CANTalon leftMotorOne = RobotMap.leftMotorOne;
	CANTalon leftMotorTwo = RobotMap.leftMotorTwo;
	CANTalon rightMotorOne = RobotMap.rightMotorOne;
	CANTalon rightMotorTwo = RobotMap.rightMotorTwo;
	
	public void controlDriveTrain() {
		ControlDriveTrain controlDriveTrain = new ControlDriveTrain(leftMotorOne, leftMotorTwo, rightMotorOne, rightMotorTwo);
		controlDriveTrain.start();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

