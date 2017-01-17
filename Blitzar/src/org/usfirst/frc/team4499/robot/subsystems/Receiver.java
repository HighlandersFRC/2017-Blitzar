package org.usfirst.frc.team4499.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.commands.*;
import com.ctre.CANTalon;

/**
 *
 */
public class Receiver extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public void controlReceiver() {
		RobotMap.receiverLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		RobotMap.receiverRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		ControlReceiver controlReceiver = new ControlReceiver();
		controlReceiver.start();
	}
	
	public void disableReceiver() {
		RobotMap.receiverLeft.changeControlMode(CANTalon.TalonControlMode.Current);
		RobotMap.receiverRight.changeControlMode(CANTalon.TalonControlMode.Current);
		RobotMap.receiverLeft.set(0);
		RobotMap.receiverRight.set(0);
		Robot.receiverPower = 0;
	}
	
	
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

