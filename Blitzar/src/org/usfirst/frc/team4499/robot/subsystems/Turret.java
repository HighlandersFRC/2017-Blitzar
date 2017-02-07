package org.usfirst.frc.team4499.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4499.robot.commands.*;

/**
 *
 */
public class Turret extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	
	
	public void controlTurretPower() {
		ControlTurret controlTurret = new ControlTurret();
		controlTurret.start();
	}
	
	public void controlTurretPositionRelative(double angle) {
		System.out.println("Started controlTurretPositionRelative");
		TurretMP turretMP = new TurretMP(angle);
		turretMP.start();
	}
	
	public void controlTurretAbsolute() {
		
	}
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

