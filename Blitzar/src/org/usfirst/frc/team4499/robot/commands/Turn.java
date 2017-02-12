package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.PID;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command {
	private double kI = 0.001; //0.0002
 	private double kP = 0.03; //0.02
	private double kD = 0.1; //0.05
	private double target = 0;
	private int whichWay = 0;
	private boolean absolute;
	private double degrees;
	double startingAngle;
	Preferences prefs;
	PID orientation = new PID(kP,kI,kD);
	public Turn (double degrees, boolean absolute){
		
		orientation.setMaxOutput(.4);
		orientation.setMinOutput(-.4);
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
		//return Math.abs(target - RobotMap.navx.getYaw()) < 2 && orientation.getResult() <.05;
		return Math.abs(target - RobotMap.navx.getYaw()) < 1;
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
