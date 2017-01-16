package org.usfirst.frc.team4499.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.commands.*;
import com.ctre.CANTalon;

/**
 *
 */
public class Flywheel extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public void controlFlywheel() {
		RobotMap.flywheel.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		ControlFlywheel controlFlywheel = new ControlFlywheel();
		controlFlywheel.start();
	}
	
	public void disableFlywheel() {
		RobotMap.flywheel.changeControlMode(CANTalon.TalonControlMode.Current);
		RobotMap.flywheel.set(0);
	}
	
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

