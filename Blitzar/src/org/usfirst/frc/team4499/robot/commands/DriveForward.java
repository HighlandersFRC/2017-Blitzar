package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.RobotStats;
import org.usfirst.frc.team4499.robot.tools.DCMotor;
import org.usfirst.frc.team4499.robot.tools.PID;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class DriveForward extends Command{
	// PID Values for this motor
	
	private double kI = 0.001;
	private double kP = .13;
	private double kD = 1;
	
	//private double ticksPerRotation = 4320 * 3;
	private double ticksPerRotation = 4096 / 0.3846153846153846; //Gearing ratio is 13:5  5/13 = 0.38461538
	private double startAngle;
	private double rightSide;
	private double leftSide;
	private double average;
	private boolean instantlyFinish = false;
	double rotations; // distance to travel
	PID rightWheel = new PID(kP,kI,kD); // PID's for both motors
	PID leftWheel = new PID(kP,kI,kD);
	PID orientation = new PID(0.025, 0.0001, 0);
	DCMotor rightEncMotor = RobotMap.rightMotorOne;
	DCMotor leftEncMotor = RobotMap.leftMotorOne;
	
	
	
    
	public DriveForward(double inches) {
        //TODO once PID is working change this to distance
		if (rightEncMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute) 
				== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent
				&&  leftEncMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute) 
				== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
    	this.rotations = inches / (RobotStats.driveDiameter * Math.PI);
		} else {
		//	this.rotations = 0;
			instantlyFinish = true;
		}
    }
	public DriveForward(double feet, double inches){
		if (rightEncMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute) 
				== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent
				&&  leftEncMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Absolute) 
				== CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
		this.rotations = (feet * 12 + inches) / (RobotStats.driveDiameter * Math.PI);
		} else {
			//this.rotations = 0;
			instantlyFinish = true;
		}
		
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.rightMotorOne.zeroEncoder();
    	RobotMap.rightMotorTwo.zeroEncoder();
    	RobotMap.leftMotorOne.zeroEncoder();
    	RobotMap.leftMotorTwo.zeroEncoder();
    	
    	startAngle = RobotMap.navx.getAngle();
    	
    	orientation.setSetPoint(startAngle);
    	//rightEncMotor.changeControlMode(TalonControlMode.Position);
    	//leftEncMotor.changeControlMode(TalonControlMode.Position);
    	//RobotMap.rightMotorTwo.changeControlMode(TalonControlMode.Follower);
    	//RobotMap.leftMotorTwo.changeControlMode(TalonControlMode.Follower);
    	rightEncMotor.setPID(kI, kP, kD);
    	leftEncMotor.setPID(kP, kI, kD);
    	orientation.setPID(0.025, 0.0001, 0.2);
    	System.out.println("Rotations " + rotations);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	leftWheel.setSetPoint(rotations);
    	rightWheel.setSetPoint(-rotations);
    	//rightWheel.setSetPoint(distance);
    	leftWheel.updatePID(leftEncMotor.getEncPosition()/ticksPerRotation);
    	rightWheel.updatePID(rightEncMotor.getEncPosition()/ticksPerRotation);
    	orientation.updatePID(RobotMap.navx.getAngle());
    	//rightWheel.updatePID(RobotMap.motorRightTwo.getEncPosition());
    	
    	//System.out.println("Target" + rightWheel.getSetPoint() + "Wheel Position: " + (((double)RobotMap.motorRightTwo.getEncPosition())/7500.0));
    	//SmartDashboard.putNumber("Distance to Go",-(rightWheel.getSetPoint()-(((double)rightEncMotor.getEncPosition())/ticksPerRotation)));
    	//System.out.println("Distance to Go" + (-1 * (rightWheel.getSetPoint()-(((double)rightEncMotor.getEncPosition())/ticksPerRotation))));
    	RobotMap.leftMotorOne.set(-leftWheel.getResult()- (0.7 * orientation.getResult()));
    	RobotMap.leftMotorTwo.set(-leftWheel.getResult()- (0.7 * orientation.getResult()));
    	RobotMap.rightMotorOne.set(-rightWheel.getResult() - (0.7 * orientation.getResult()));
    	RobotMap.rightMotorTwo.set(-rightWheel.getResult() - (0.7 * orientation.getResult()));
    	//System.out.println("orientation result " + orientation.getResult());
    	
    	
    	/*rightEncMotor.set(-rotations);
    	leftEncMotor.set(-rotations);
    	RobotMap.rightMotorTwo.set(rightEncMotor.getDeviceID());
    	RobotMap.leftMotorTwo.set(leftEncMotor.getDeviceID());*/
    	
    	//rightSide = (Math.abs((((double)rightEncMotor.getEncPosition())/ticksPerRotation)));
    	//leftSide = (Math.abs((((double)leftEncMotor.getEncPosition())/ticksPerRotation)));
    	//average = ((Math.abs(rightSide) + Math.abs(leftSide)) / 2);
    	//System.out.println("rightside leftside " + rightSide + " " + leftSide);
    	
    	//System.out.println("rightEncMotor.getEncPosition/ticksPerRotation " + rightEncMotor.getEncPosition()/ticksPerRotation);
    //	System.out.println("Average, Rotations " + average + " " + rotations);
    	
    	
    	//System.out.println("Right enc pos : " + rightEncMotor.getEncPosition());
    	//System.out.println("Lefte enc pos : " + leftEncMotor.getEncPosition());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	rightSide = (Math.abs((((double)rightEncMotor.getEncPosition())/ticksPerRotation)));
    	leftSide = (Math.abs((((double)leftEncMotor.getEncPosition())/ticksPerRotation)));
    	//rightSide = (Math.abs((rightWheel.getSetPoint()-(((double)rightEncMotor.getEncPosition())/ticksPerRotation))));
    	//leftSide = (Math.abs((rightWheel.getSetPoint()-(((double)leftEncMotor.getEncPosition())/ticksPerRotation))));
    	//boolean rightSideDone = (Math.abs((rightWheel.getSetPoint()-(((double)rightEncMotor.getEncPosition())/ticksPerRotation)))) < .15;
    	//boolean leftSideDone = (Math.abs((rightWheel.getSetPoint()-(((double)leftEncMotor.getEncPosition())/ticksPerRotation)))) < .15;
    	average = ((Math.abs(rightSide) + Math.abs(leftSide)) / 2);
    	//double smallest = Math.min(rightSide, leftSide);
       // return rightSideDone && leftSideDone;
    	
    	if (instantlyFinish) {
    		return true;
    	} else {
    	return (Math.abs(average - rotations) < 0.15); 
    	}
    	//return (Math.abs((smallest - rotations)) < 0.15);
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	RobotMap.leftMotorOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.leftMotorTwo.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.rightMotorOne.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.rightMotorTwo.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.leftMotorOne.set(0);
    	RobotMap.leftMotorTwo.set(0);
    	
    	RobotMap.rightMotorOne.set(0);
    	RobotMap.rightMotorTwo.set(0);
    	
    	System.out.println("Distance to Go" + (-1 * (rightWheel.getSetPoint()-(((double)rightEncMotor.getEncPosition())/ticksPerRotation))));
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    	
    }
}
