package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.CANTalon;

import org.usfirst.frc.team4499.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class ControlFlywheel extends Command {

	private float flyWheelPower;
	private CANTalon.TalonControlMode talonControlMode;
	
    public ControlFlywheel(CANTalon.TalonControlMode talonControlMode) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.talonControlMode = talonControlMode;
    	RobotMap.flywheelMaster.changeControlMode(talonControlMode);
    	requires(Robot.flywheel);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	flyWheelPower = Robot.flyWheelPower;
    	RobotMap.flywheelMaster.setAllowableClosedLoopErr(0);
    	RobotMap.flywheelMaster.clearIAccum();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    	
    	//// Control when in VBus Mode
    	
    	if (talonControlMode == CANTalon.TalonControlMode.PercentVbus) {
	    	// Increase/Decrease flywheel power
	    	if (OI.flyWheelSpeedIncrease.get()) {
	    		// Increase speed
	    		flyWheelPower -= 0.01; // Negative is the correct direction, speed increase -> negative
	    	}
	    	if (OI.flyWheelSpeedDecrease.get()) {
	    		// Decrease speed
				flyWheelPower += 0.01; // Negative is the correct direction, speed decrease -> positive
			}
	    	
	    	// Keep motor within 0 throttle and full speed in the correct direction
			if (flyWheelPower < -1) {
				flyWheelPower = -1;
				System.out.println("Setting flywheel power to -1 because it was less than -1");
			}
			if (flyWheelPower > 0) {
				flyWheelPower = 0;
				System.out.println("Setting flywheel power to 0 because it was greater than 0");
			}
			
			RobotMap.flywheelMaster.set(flyWheelPower);
    	}
		
    	
   //// Control when in Speed Mode
    	
       	if (talonControlMode == CANTalon.TalonControlMode.Speed) {
   	    	// Increase/Decrease flywheel power
   	    	if (OI.flyWheelSpeedIncrease.get()) {
   	    		// Increase speed
   	    		//System.out.println("Increasing flywheel speed");
   	    		flyWheelPower = flyWheelPower - 10; // Negative is the correct direction, speed increase -> negative
   	    	}
   	    	if (OI.flyWheelSpeedDecrease.get()) {
   	    		// Decrease speed
   	    		//System.out.println("Decreasing flywheel speed");
   				flyWheelPower = flyWheelPower + 10; // Negative is the correct direction, speed decrease -> positive
   			}
   	    	
   	    	// Keep motor within 0 throttle and full speed in the correct direction
   			if (flyWheelPower < -5000) {
   				flyWheelPower = -5000;
   				System.out.println("Setting flywheel speed to -5000 because it was faster than -5000");
   			}
   			if (flyWheelPower > 0) {
   				flyWheelPower = 0;
   				System.out.println("Setting flywheel speed to 0 because it was above 0");
   			}
   			
   			
   			
   			// flyWheelPower is currently in RPM
   	    	// Convert flyWheelPower so that the talon will run at the desired RPM
   	    	//System.out.println("Flywheel power was " + flyWheelPower + " in RPM");
   	    //flyWheelPower = ((flyWheelPower * 4096) / 600);
   	    	// Now ready to be set to Talon'
   	    	//System.out.println("Now it is " + flyWheelPower + " in encoder velocity");
   			
   			RobotMap.flywheelMaster.set(flyWheelPower);
   			
   			// Get flywheel power back to RPM
   			//System.out.println("Flywheel power was " + flyWheelPower + " in velocity");
   		//flyWheelPower = ((flyWheelPower / 4096) * 600);
   			//System.out.println("Now it is " + flyWheelPower + " in RPM");
       	}
    	//System.out.println("Target RPM: " + flyWheelPower);
    	//System.out.println("Flywheel power " + flyWheelPower);
    	
    	
		
		// Prints twice so that one can be a progress bar, and the other can be a raw value
		SmartDashboard.putNumber("Flywheel power", -flyWheelPower);
		SmartDashboard.putNumber("Flywheel Power value", -flyWheelPower);
		//System.out.println("flyWheelPower " + flyWheelPower);
		//System.out.println("Flywheel speed " + RobotMap.flywheel.getEncVelocity());
		//Robot.flyWheelPower = flyWheelPower;
		
		
		Robot.flyWheelPower = flyWheelPower;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (!OI.flyWheelSpeedDecrease.get() && !OI.flyWheelSpeedIncrease.get());
    }

    // Called once after isFinished returns true
    protected void end() {
    	  System.out.println("Finished ControlFlywheel");
    	  
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
