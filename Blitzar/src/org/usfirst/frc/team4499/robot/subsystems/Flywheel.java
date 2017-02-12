package org.usfirst.frc.team4499.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.RobotStats;
import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.tools.Tegra;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public class Flywheel extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public static float maxFlywheelVelocity = RobotStats.maxFlywheelVelocity; // Ticks per second
	public static float maxFlywheelAcceleration = RobotStats.maxFlywheelAcceleration; // Ticks per second^2

	public void controlFlywheelPercentVBus() {
		RobotMap.flywheel.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		ControlFlywheel controlFlywheel = new ControlFlywheel(CANTalon.TalonControlMode.PercentVbus);
		controlFlywheel.start();
	}
	
	public void controlFlywheelVelocityMP(double desiredVelocity) {
		RobotMap.flywheel.changeControlMode(CANTalon.TalonControlMode.MotionProfile);
		//                                                                                 Goal            MPTalon          Subsystem
		OneDimensionalVelocityMP flywheelMotionProfile = new OneDimensionalVelocityMP(desiredVelocity, RobotMap.flywheel, Robot.flywheel);
		flywheelMotionProfile.start();
	}
	
	public void autoControlSpeed() {
		RobotMap.flywheel.changeControlMode(TalonControlMode.Speed);
		double dist = Tegra.distance;
		double flywheelSetSpeed = (0.0617485403 * (dist * dist) - (0.7296590798 * dist) + 3170.5521882763);
		setFlywheelVelocity setVelocity = new setFlywheelVelocity(flywheelSetSpeed);
	}
	
	public void controlFlywheelVelocity() {
		//RobotMap.flywheel.setP(0.02);
		//RobotMap.flywheel.setD(0.5);
		
		RobotMap.flywheel.changeControlMode(CANTalon.TalonControlMode.Speed);
		ControlFlywheel controlFlywheel = new ControlFlywheel(CANTalon.TalonControlMode.Speed);
		controlFlywheel.start();
	}
	
	public void disableFlywheel() {
		RobotMap.flywheel.changeControlMode(CANTalon.TalonControlMode.Current);
		Robot.flyWheelPower = 0;
		RobotMap.flywheel.set(0);
	}
	
	public void accelerateToVelocity(float desiredVelocity) {
		
	}
	
	public void turnToAngleRadiansAbsolute() {
		
	}
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

