package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.DCMotor;
import org.usfirst.frc.team4499.robot.tools.PID;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class NavXDriveForward extends Command {
	private double speed;
	private double time;
	private double distance;
	private double startAngle;
	private double kp = 0.025;
	private double ki = 0.0001;
	private double kd = 0;
	private DCMotor leftMotor=RobotMap.leftMotorOne;
	private DCMotor rightMotor=RobotMap.rightMotorOne;
	
	private double kIZone = Double.MAX_VALUE;
	private PID orientation; 
	private double startTime;
	private boolean across = false;
    public NavXDriveForward(double speed, double time) {
    	this.speed = speed;
    	this.time = time;
    	orientation = new PID(kp,ki,kd, kIZone);
    	//orientation.setMaxOutput(.25);
    	//orientation.setMinOutput(-.25);
    	orientation.setContinuous(true);
    	orientation.setMaxInput(360);
    	orientation.setMinInput(0);
    	    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	startAngle = RobotMap.navx.getAngle();
    	orientation.setSetPoint(startAngle);
    	startTime = Timer.getFPGATimestamp();
    	
    }

    // Csalled repeatedly when this Command is scheduled to run
    protected void execute() {
    	orientation.updatePID(RobotMap.navx.getAngle());
    	RobotMap.leftMotorOne.set(-orientation.getResult()-speed);
    	RobotMap.leftMotorTwo.set(-orientation.getResult()-speed);
    	
    	RobotMap.rightMotorOne.set(-orientation.getResult()+speed);
    	RobotMap.rightMotorTwo.set(-orientation.getResult()+speed);
    	
    
    	//RobotMap.motorLeftOne.set(- speed);
    	//RobotMap.motorLeftTwo.set(- speed);
    	
    	//RobotMap.motorRightOne.set(speed);
    	//RobotMap.motorRightTwo.set(speed);
    	//System.out.println("Orientation: " + RobotMap.navx.getAngle()+"Start Angle:"+ startAngle+ "Response:" +  orientation.getResult());
    	//if(RobotMap.navx.getWorldLinearAccelZ() < -.5 &&!across){
    	//	time = 2 + Timer.getFPGATimestamp();
    	//	across = true;
    	//	speed = .3;

    	//}/
    	
    	//System.out.println(orientation.getResult());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return startTime + time <= Timer.getFPGATimestamp();
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.leftMotorOne.set(0);
    	RobotMap.leftMotorTwo.set(0);
    	
    	RobotMap.rightMotorOne.set(0);
    	RobotMap.rightMotorTwo.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
