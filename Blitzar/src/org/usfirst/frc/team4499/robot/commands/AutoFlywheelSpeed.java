package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.Tegra;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoFlywheelSpeed extends Command {

	private double flywheelSetSpeed;
	private double dist;
	
    public AutoFlywheelSpeed() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.flywheel);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.flywheel.changeControlMode(TalonControlMode.Speed);
    	RobotMap.flywheel.setAllowableClosedLoopErr(0);
    	RobotMap.flywheel.clearIAccum();
    	if (Tegra.x != -1) {
		dist = Tegra.distance;
		flywheelSetSpeed = (0.0617485403 * (dist * dist) - (0.7296590798 * dist) + 3170.5521882763);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Tegra.x != -1) {
    	dist = Tegra.distance;
    	flywheelSetSpeed = (0.0617485403 * (dist * dist) - (0.7296590798 * dist) + 3170.5521882763);
    	RobotMap.flywheel.set(-flywheelSetSpeed);
    	System.out.println("Setting flywheel speed");
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.flywheel.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	RobotMap.flywheel.set(0);
    }
}
