package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.subsystems.Agitator;
import org.usfirst.frc.team4499.robot.subsystems.Receiver;
import org.usfirst.frc.team4499.robot.subsystems.Vortex;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoAgitator extends Command {

	public static Vortex vortex = new Vortex();
	public static Agitator agitator = new Agitator();
	public static Receiver receiver = new Receiver();
	private int fireCount = 0;
	
	
	
    public AutoAgitator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.vortex);
    	requires(Robot.agitator);
    	requires(Robot.receiver);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (fireCount > 20 && fireCount <= 40) {
			// Run reverse
			vortex.setVortexPower(-1);
			receiver.setReceiverPower(1);
			RobotMap.agitatorMotor.set(-1);
		} else {
		// Run forward
		vortex.setVortexPower(-1);
		receiver.setReceiverPower(1);
		RobotMap.agitatorMotor.set(1);
		}
		fireCount++;
		if (fireCount % 40 == 0) {
			fireCount = 0;
		}
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
    
    protected boolean isInterruptable() {
    	return true;
    }
}
