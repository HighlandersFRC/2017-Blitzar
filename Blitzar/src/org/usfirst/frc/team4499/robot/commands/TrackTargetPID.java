package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.PID;
import org.usfirst.frc.team4499.robot.tools.Tegra;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TrackTargetPID extends Command {
	
	private float lastX = -1;
	private float currentX;
	private double kP = 0.0013;
	private double kI = 0.00001;
	private double kD = 0.00015;
	private double kIZone = 200;
	PID orientation = new PID(kP,kI,kD, kIZone);
	
    public TrackTargetPID() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	
    	orientation.setMaxOutput(.35);
		orientation.setMinOutput(-.35);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	
		orientation.setSetPoint(160);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if (Tegra.x && lastX)
    	currentX = Tegra.x;
    	orientation.updatePID(currentX);
    	System.out.println("Updated to " + currentX);
    	if (currentX == -1) {
    		RobotMap.turretMotor.set(0);
    	} else {
    		if (Tegra.theta < 0) {
    			RobotMap.turretMotor.set(-orientation.getResult() - 0.11);
    		} else if (Tegra.theta > 0) {
    			RobotMap.turretMotor.set(-orientation.getResult() + 0.11);
    		}
    	}
    	
    	if (RobotMap.turretMotor.getEncPosition() > -3.1 && RobotMap.turretMotor.getEncPosition() < 3.4 ) {
    	
    	System.out.println("Angle " + RobotMap.turretMotor.getPosition());
    	System.out.println("SetPoint " + 160 + " Error " + (160 - currentX));
    	System.out.println("Set power to " + -orientation.getResult());
    	System.out.println("Tegra X " + currentX);
    	lastX = currentX;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("TRACKING JUST ENDED");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
