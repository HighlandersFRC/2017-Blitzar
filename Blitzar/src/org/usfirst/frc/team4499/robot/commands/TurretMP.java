package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */


public class TurretMP extends Command {

	double setAngle;
	double previousPosition;
	boolean prevPosInRange;
	boolean posInRange;
	
	
    public TurretMP(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.turret);
    	setAngle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//RobotMap.turretMotor.disable();
    	RobotMap.turretMotor.changeControlMode(TalonControlMode.MotionMagic);
    	RobotMap.turretMotor.setPID(0.45, 0.003, 0);
    	//RobotMap.turretMotor.setF(0.2);
    	RobotMap.turretMotor.setAllowableClosedLoopErr(0);
    	RobotMap.turretMotor.setMotionMagicCruiseVelocity(320); //max 420
    	RobotMap.turretMotor.setMotionMagicAcceleration(1500);
    	RobotMap.turretMotor.configNominalOutputVoltage(0, 0);
    	RobotMap.turretMotor.configPeakOutputVoltage(12, -12);
    	//RobotMap.turretMotor.clearMotionProfileTrajectories();
    	//RobotMap.turretMotor.enable();
    	//System.out.println("Initialized TurretMP");
    	RobotMap.turretMotor.set(setAngle);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//RobotMap.turretMotor.set(setAngle);
    	System.out.println("Trying to get to " + setAngle + " C: " + RobotMap.turretMotor.getPosition());
    	previousPosition = RobotMap.turretMotor.getPosition();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	prevPosInRange = Math.abs(previousPosition - setAngle) < 0.01;
    	posInRange = Math.abs(RobotMap.turretMotor.getPosition() - setAngle) < 0.01;
        return (prevPosInRange && posInRange);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Got to desired position.");
    	Robot.gotToPosition = true;
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.gotToPosition = true;
    }
}
