package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.RobotStats;
import org.usfirst.frc.team4499.robot.tools.DCMotor;
import org.usfirst.frc.team4499.robot.tools.PID;

public class DriveForward extends Command{
	// PID Values for this motor
	
	private double kI = 0.001;
	private double kP = .2;
	private double kD = 0;
	
	private double ticksPerRotation = 4320 * 3;
	double rotations; // distance to travel
	PID rightWheel = new PID(kP,kI,kD); // PID's for both motors
	PID leftWheel = new PID(kP,kI,kD);
	DCMotor rightEncMotor = RobotMap.rightMotorOne;
	DCMotor leftEncMotor = RobotMap.leftMotorOne;
	
	
	
    
	public DriveForward(double inches) {
        //TODO once PID is working change this to distance
    	this.rotations = inches / (RobotStats.driveDiameter * Math.PI);
    }
	public DriveForward(double feet, double inches){
		this.rotations = (feet * 12 + inches) / (RobotStats.driveDiameter * Math.PI);
		
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.rightMotorOne.zeroEncoder();
    	RobotMap.rightMotorTwo.zeroEncoder();
    	RobotMap.leftMotorOne.zeroEncoder();
    	RobotMap.leftMotorTwo.zeroEncoder();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftWheel.setSetPoint(rotations);
    	rightWheel.setSetPoint(-rotations);
    	//rightWheel.setSetPoint(distance);
    	leftWheel.updatePID(leftEncMotor.getEncPosition()/ticksPerRotation);
    	rightWheel.updatePID(rightEncMotor.getEncPosition()/ticksPerRotation);
    	//rightWheel.updatePID(RobotMap.motorRightTwo.getEncPosition());
    	
    	//System.out.println("Target" + rightWheel.getSetPoint() + "Wheel Position: " + (((double)RobotMap.motorRightTwo.getEncPosition())/7500.0));
    	//SmartDashboard.putNumber("Distance to Go",-(rightWheel.getSetPoint()-(((double)rightEncMotor.getEncPosition())/ticksPerRotation)));
    	//System.out.println("Distance to Go" + (-1 * (rightWheel.getSetPoint()-(((double)rightEncMotor.getEncPosition())/ticksPerRotation))));
    	RobotMap.leftMotorOne.set(-leftWheel.getResult());
    	RobotMap.leftMotorTwo.set(-leftWheel.getResult());
    	RobotMap.rightMotorOne.set(-rightWheel.getResult());
    	RobotMap.rightMotorTwo.set(-rightWheel.getResult());
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean rightSideDone = (Math.abs((rightWheel.getSetPoint()-(((double)rightEncMotor.getEncPosition())/ticksPerRotation)))) < .15;
    	boolean leftSideDone = (Math.abs((rightWheel.getSetPoint()-(((double)leftEncMotor.getEncPosition())/ticksPerRotation)))) < .15;
        return rightSideDone && leftSideDone;
    }

    // Called once after isFinished returns true
    protected void end() {
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
