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
public class NavXDriveForwardDistance extends Command {
	private double speed;
	private double distance;
	private double firstDistance;
	private double startAngle;
	private double kp = 0.025;
	private double ki = 0.0001;
	private double kd = 0;
	private double kIZone = Double.MAX_VALUE;
	private PID orientation; 
	private double startTime;
	private boolean across = false;
    public NavXDriveForwardDistance(double speed, double distance) {
    	this.distance = distance*811.359;
    	this.speed = speed;
    	this.firstDistance = this.distance * .75;
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
    	if(rightMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative)
    			== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent
    			&& leftMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative)
    			== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent)  {
    		encodersPresent=true;
    		
    	} */
    	
    	startAngle = RobotMap.navx.getAngle();
    	orientation.setSetPoint(startAngle);
    	startTime = Timer.getFPGATimestamp();
    	
    	RobotMap.leftMotorOne.zeroEncoder();
    	RobotMap.rightMotorOne.zeroEncoder();
    	
    }

    // Csalled repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Math.abs((double)((RobotMap.leftMotorOne.getEncPosition())-(RobotMap.rightMotorOne.getEncPosition()))/2.0)<this.firstDistance)
    	{
    	orientation.updatePID(RobotMap.navx.getAngle());
    	RobotMap.leftMotorOne.set(-orientation.getResult()-speed*2);
    	RobotMap.leftMotorTwo.set(-orientation.getResult()-speed*2);
    	
    	RobotMap.rightMotorOne.set(-orientation.getResult()+speed*2);
    	RobotMap.rightMotorTwo.set(-orientation.getResult()+speed*2);
    	
    	}
    	else 
    	{
    		orientation.updatePID(RobotMap.navx.getAngle());
        	RobotMap.leftMotorOne.set(-orientation.getResult()-speed);
        	RobotMap.leftMotorTwo.set(-orientation.getResult()-speed);
        	
        	RobotMap.rightMotorOne.set(-orientation.getResult()+speed);
        	RobotMap.rightMotorTwo.set(-orientation.getResult()+speed);
    	}
    	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
   //     return startTime + time <= Timer.getFPGATimestamp();
    	
    	return Math.abs((double)((RobotMap.leftMotorOne.getEncPosition())-(RobotMap.rightMotorOne.getEncPosition()))/2.0)>this.distance; 
    	
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
