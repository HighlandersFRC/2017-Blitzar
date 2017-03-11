package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4499.robot.*;
import com.ctre.CANTalon;



/**
 *
 */
public class SetFlywheelVelocity extends Command {

	double setVelocity;
	
    public SetFlywheelVelocity(double desiredVelocity) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.flywheel);
    	//RobotMap.flywheelMaster.setAllowableClosedLoopErr(0);
    	//RobotMap.flywheelMaster.clearIAccum();
    	setVelocity = desiredVelocity;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.flywheelMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
    //	RobotMap.flywheelMaster.set(setVelocity);
    	RobotMap.flywheelMaster.set(setVelocity);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RobotMap.flywheelMaster.set(setVelocity);
    	System.out.println("setpoint " + RobotMap.flywheelMaster.getSetpoint());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return (Math.abs(setVelocity - RobotMap.flywheel.getSpeed()) < 10);
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("End");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("setFlywheelVelocity interrupted");
    }
}
