package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.PID;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command {
	private double kI = 0.0001; //
 	private double kP = 0.008; // 
	private double kD = 0; //
	private double kIZone = 20;
	private double target = 0;
	private int whichWay = 0;
	private boolean absolute;
	private double degrees;
	
	float previousYaw;
	float outdatedYaw;
	float previousYawPlaceholder;
	float outdatedYawPlaceholder;
	
	double startingAngle;
//	Preferences prefs;
	PID orientation = new PID(kP,kI,kD, kIZone);
	public Turn (double degrees, boolean absolute){
		
		orientation.setMaxOutput(.5); 
		orientation.setMinOutput(-.5); 
		orientation.setContinuous(true);
		orientation.setMaxInput(360);
		orientation.setMinInput(0);
		while(degrees > 360){
			degrees = degrees - 360;
			
		}
		while(degrees < -360){
			degrees = degrees + 360;
		}
		this.degrees = degrees;
		this.absolute = absolute;	
	}
	
	@Override
	protected void initialize() {	
		//RobotMap.navx.zeroYaw();
		
		previousYaw = RobotMap.navx.getYaw();
		outdatedYaw = RobotMap.navx.getYaw();
		
		if(absolute == false){
			startingAngle = RobotMap.navx.getYaw();
			target = startingAngle + degrees;
		} else {
			target = degrees;
		}
		
		orientation.setSetPoint(target);
		System.out.println("NavX starting at " + startingAngle + " Target: " + target);
		
	}
	
	
	

	@Override
	protected void execute() {

		orientation.updatePID(RobotMap.navx.getYaw());
    	
    	RobotMap.leftMotorOne.set(-orientation.getResult());
    	RobotMap.leftMotorTwo.set(-orientation.getResult());
    	
    	RobotMap.rightMotorOne.set(-orientation.getResult());
    	RobotMap.rightMotorTwo.set(-orientation.getResult());
		System.out.println("Angle: " + RobotMap.navx.getYaw() + " Target: " + target);
		System.out.println("orientation.getResult() " + orientation.getResult());
	}

	@Override
	protected void interrupted() {
		RobotMap.leftMotorOne.set(0);
		RobotMap.leftMotorTwo.set(0);
		RobotMap.rightMotorOne.set(0);
		RobotMap.rightMotorTwo.set(0);
	}

	@Override
	protected boolean isFinished() {
		
		previousYawPlaceholder = previousYaw;
		outdatedYawPlaceholder = outdatedYaw;
		
		boolean outdatedYawInRange = Math.abs(target - outdatedYaw) < .5;
		boolean previousYawInRange = Math.abs(target - previousYaw) < .5;
		boolean yawInRange = Math.abs(target - RobotMap.navx.getYaw()) < 0.5;
		
		outdatedYaw = previousYaw;
		previousYaw = RobotMap.navx.getYaw();
		if (outdatedYawInRange && previousYawInRange && yawInRange) {
			System.out.println("Finished turn, " + outdatedYawPlaceholder + " " + previousYawPlaceholder + " " + RobotMap.navx.getYaw() + " Goal: " + target);
			return true;
		} else {
			return false;
		}
		//return (outdatedYawInRange && previousYawInRange && yawInRange && (orientation.getResult() < 1));
		
		//return Math.abs(target - RobotMap.navx.getYaw()) < 2 && orientation.getResult() <.05;
		
		//return Math.abs(target - RobotMap.navx.getYaw()) < 1;
		//return false;
	}
	
	@Override
	protected void end() {
		RobotMap.leftMotorOne.set(0);
		RobotMap.leftMotorTwo.set(0);
		RobotMap.rightMotorOne.set(0);
		RobotMap.rightMotorTwo.set(0);
		System.out.println("Finished turn");
		
	}
	
}
