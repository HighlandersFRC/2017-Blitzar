package org.usfirst.frc.team4499.robot.subsystems;

import org.usfirst.frc.team4499.robot.commands.SetAgitatorPower;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Agitator extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public void setAgitatorPower(float power) {
		SetAgitatorPower setAgitatorPower = new SetAgitatorPower(power);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

