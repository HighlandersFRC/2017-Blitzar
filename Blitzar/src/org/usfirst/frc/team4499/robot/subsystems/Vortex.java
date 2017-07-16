package org.usfirst.frc.team4499.robot.subsystems;

import org.usfirst.frc.team4499.robot.commands.SetVortexPower;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Vortex extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	
	public void setVortexPower(float setPower) {
		SetVortexPower x = new SetVortexPower(setPower);
		x.start();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

