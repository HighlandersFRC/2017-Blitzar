package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.DCMotor;
import org.usfirst.frc.team4499.robot.tools.PID;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class NavXDriveForwardDistance extends Command {
	private double speed;
	private double distance;
	private double firstDistance;
	private double startAngle;
	private double kp = 0.013;
	private double ki = 0.0001;
	private double kd = 0;
	private double wheelCircumference = 12.40929098167968;
	private double rotations;
	private double kIZone = Double.MAX_VALUE;
	private PID orientation; 
	private double startTime;
	private boolean across = false;
	private boolean rampUp;
    public NavXDriveForwardDistance(double speed, double distance, boolean shouldRamp) {
    	//this.distance = distance*485; comp bot
    	//this.distance = distance * 673.87;
    	this.rotations = distance/wheelCircumference;
    	this.distance =  this.rotations*1.51*4096;
    	this.speed = speed;
    	this.firstDistance = this.distance * .5;
    	this.rampUp = shouldRamp;
    	orientation = new PID(kp,ki,kd, kIZone);
    	//orientation.setMaxOutput(.25);
    	//orientation.setMinOutput(-.25);
    	orientation.setContinuous(true);
    	orientation.setMaxInput(360);
    	orientation.setMinInput(0);
    	
    	    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	/*System.out.println("Right"+rightMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative));
    	System.out.println("Left"+leftMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative));
    	if(rightMotor.isSensorPre0sent(FeedbackDevice.CtreMagEncoder_Relative)
    			== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent
    			&& leftMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative)
    			== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent)  {
    		encodersPresent=true;
    		
    	}*/
    	
    	RobotMap.rightMotorOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.rightMotorTwo.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.leftMotorOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.leftMotorTwo.changeControlMode(TalonControlMode.PercentVbus);
    	
    	// practice bot
    	
    //	RobotMap.rightMotorOne.changeControlMode(TalonControlMode.Position);
    //	RobotMap.leftMotorOne.changeControlMode(TalonControlMode.Position);
    //	RobotMap.rightMotorTwo.changeControlMode(TalonControlMode.Follower);
    //	RobotMap.leftMotorTwo.changeControlMode(TalonControlMode.Follower);
    //	RobotMap.rightMotorTwo.set(3);
    //	RobotMap.leftMotorTwo.set(1);
    	//RobotMap.rightMotorTwo.enable();
    	//RobotMap.leftMotorTwo.enable();
    	
    	startAngle = RobotMap.navx.getAngle();
    	orientation.setSetPoint(startAngle);
    	startTime = Timer.getFPGATimestamp();
    	
    	RobotMap.leftMotorOne.zeroEncoder();
    	RobotMap.leftMotorTwo.zeroEncoder();
    	RobotMap.rightMotorTwo.zeroEncoder();
    	RobotMap.rightMotorOne.zeroEncoder();
    	
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Math.abs((double)((RobotMap.leftMotorOne.getEncPosition())-
    	//		(RobotMap.rightMotorOne.getEncPosition()))/2.0)<this.firstDistance) {
    			(RobotMap.rightMotorTwo.getEncPosition()))/2.0)<this.firstDistance) {
    			
    		setMotorPowerNavXPID(2.5);
    	} else {
    		setMotorPowerNavXPID(1);
    	}
    }

	private void setMotorPowerNavXPID(double multiplier) {
		if (!rampUp) {
			multiplier = 1;
		}
		
		orientation.updatePID(RobotMap.navx.getAngle());
		RobotMap.leftMotorOne.set(-orientation.getResult()-speed*multiplier);
		RobotMap.leftMotorTwo.set(-orientation.getResult()-speed*multiplier);
		
		RobotMap.rightMotorOne.set(-orientation.getResult()+speed*multiplier);
		RobotMap.rightMotorTwo.set(-orientation.getResult()+speed*multiplier);
		System.out.println("Left"+RobotMap.leftMotorOne.getEncPosition());
		System.out.println("Right"+RobotMap.rightMotorTwo.getEncPosition());
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
   //     return startTime + time <= Timer.getFPGATimestamp();
    	
    	// comp bot return Math.abs((double)((RobotMap.leftMotorOne.getEncPosition())-(RobotMap.rightMotorTwo.getEncPosition()))/2.0)>this.distance; 
    	 return Math.abs((double)((RobotMap.leftMotorOne.getEncPosition())-(RobotMap.rightMotorTwo.getEncPosition()))/2.0)>this.distance;
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
