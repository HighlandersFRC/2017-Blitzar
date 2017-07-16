package org.usfirst.frc.team4499.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.RobotStats;
import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.tools.Tegra;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
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
		RobotMap.flywheelMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		ControlFlywheel controlFlywheel = new ControlFlywheel(CANTalon.TalonControlMode.PercentVbus);
		controlFlywheel.start();
	}
	
	public void controlFlywheelVelocityMP(double desiredVelocity) {
		
		if (RobotMap.flywheelMaster.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute) 
				== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent){
		RobotMap.flywheelMaster.changeControlMode(CANTalon.TalonControlMode.MotionMagic);
		SetFlywheelVelocityMP setFlywheelVelocityMP = new SetFlywheelVelocityMP(desiredVelocity);
		setFlywheelVelocityMP.start();
		
		} else {
			System.out.println("Flywheel encoder not detected!");
		}
	}
	
	public void autoControlSpeed() {
		if (RobotMap.flywheelMaster.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute) 
				== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent){
		RobotMap.flywheelMaster.changeControlMode(TalonControlMode.Speed);
		double dist = Tegra.distance;
		double flywheelSetSpeed = (0.0617485403 * (dist * dist) - (0.7296590798 * dist) + 3170.5521882763);
		SetFlywheelVelocity setVelocity = new SetFlywheelVelocity(flywheelSetSpeed);
		} else {
			System.out.println("Flywheel encoder not detected!");
		}
	}
	
	public void controlFlywheelVelocity() {
		//RobotMap.flywheel.setP(0.02);
		//RobotMap.flywheel.setD(0.5);
		
		
		if (RobotMap.flywheelMaster.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute) 
				== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent){
			RobotMap.flywheelMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
			ControlFlywheel controlFlywheel = new ControlFlywheel(CANTalon.TalonControlMode.Speed);
			controlFlywheel.start();
		} else {
			System.out.println("Flywheel encoder not detected!");
		}
		
	}
	
	public void disableFlywheel() {
		RobotMap.flywheelMaster.changeControlMode(CANTalon.TalonControlMode.Current);
		Robot.flyWheelPower = 0;
		RobotMap.flywheelMaster.set(0);
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

