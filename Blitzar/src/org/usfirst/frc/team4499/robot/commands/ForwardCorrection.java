package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.PID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ForwardCorrection extends Command {

	private double speed;
	private double distance;
	private double firstDistance;
	private double startAngle;
	private double setTarget;
	private double kp = 0.0125;
	private double ki = 0.0001;
	private double kd = 0;
	private double kIZone = Double.MAX_VALUE;
	private PID orientation; 
	private double startTime;
	private boolean across = false;
	private boolean rampUp;
	private double time;
    public ForwardCorrection(double speed, double time,double angle, boolean shouldRamp) {
    //	this.distance = distance*485;
    	this.time = time;
    	this.speed = speed;
    	this.firstDistance = this.distance * .75;
    	this.rampUp = shouldRamp;
    //	time=5;
     	orientation = new PID(kp,ki,kd, kIZone);
     	setTarget=angle;
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
    		
    	} */
  
    	startAngle = RobotMap.navx.getAngle();
    	orientation.setSetPoint(setTarget);
    	startTime = Timer.getFPGATimestamp();
    	
    	RobotMap.leftMotorOne.zeroEncoder();
    	RobotMap.rightMotorTwo.zeroEncoder();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Math.abs((double)((RobotMap.leftMotorOne.getEncPosition())-
    			(RobotMap.rightMotorTwo.getEncPosition()))/2.0)<this.firstDistance) {
    		setMotorPowerNavXPID(2);
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
     	if(startTime + time <= Timer.getFPGATimestamp())
     	{
     		return true;
     	}
     	else {
     			return false;
     		}
     			
    	
    /*	  		return Math.abs((double)((RobotMap.leftMotorOne.getEncPosition())-(RobotMap.rightMotorTwo.getEncPosition()))/2.0)>this.distance; 
    	  		
     	}
    	else
    		return true;*/
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
