package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.PID;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command {
	private double kI = 0.0001;
	private double kP = 0.03;
	private double kD = 0.2;
	private double target = 0;
	private int whichWay = 0;
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
		if(!absolute){
			target = RobotMap.navx.getYaw() + degrees;
		}
		else{
			target = degrees;
		}
		
	}
	
	@Override
	protected void initialize() {	
		//RobotMap.navx.zeroYaw();
		orientation.setSetPoint(target);
	}
	
	
	

	@Override
	protected void execute() {

		orientation.updatePID(RobotMap.navx.getYaw());
    	
    	RobotMap.leftMotorOne.set(-orientation.getResult());
    	RobotMap.leftMotorTwo.set(-orientation.getResult());
    	
    	RobotMap.rightMotorOne.set(-orientation.getResult());
    	RobotMap.rightMotorTwo.set(-orientation.getResult());
		System.out.println("Angle: " + RobotMap.navx.getYaw() + "Target: " + target);
	}

	@Override
	protected void interrupted() {
		
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(target - RobotMap.navx.getYaw()) < 2 && orientation.getResult() <.05;
		//return false;
	}
	
	@Override
	protected void end() {
	}
	
}
