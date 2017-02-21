package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class setFlywheelVelocityMP extends Command {

	private CANTalon flywheel = RobotMap.flywheel;
	private double goalVelocity;
	
    public setFlywheelVelocityMP(double desiredVelocity) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.flywheel);
    	goalVelocity = desiredVelocity;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	flywheel.setProfile(0);
    	flywheel.setMotionMagicCruiseVelocity(goalVelocity);
    	flywheel.setMotionMagicAcceleration(9200);
    	System.out.println("Initialized with goal velocity " + goalVelocity);
    	flywheel.configNominalOutputVoltage(0, 0);
    	flywheel.configPeakOutputVoltage(12, -12);
    	//flywheel.setPosition(0);
    	flywheel.set(-5000);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
