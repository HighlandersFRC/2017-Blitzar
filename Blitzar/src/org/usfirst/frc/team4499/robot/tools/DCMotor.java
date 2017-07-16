package org.usfirst.frc.team4499.robot.tools;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.RobotStats;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class DCMotor extends CANTalon {
	/*
	 * This class is designed to augment the capacities of the CANTalon class. 
	 * it will help to handle functionality that WPILib has not yet added, or simply doesn't work
	 * The current list of things this class will do is listed below
	 * Voltage Referencing
	 * Resetting encoder ticks
	 * 
	 */
	PowerDistributionPanel panel;
	int encoderZeroAdjust;
	double percentStall=.90;
	double stallCurrent = 0;
	int stallCount = 0;
	
	
	public DCMotor(int deviceNumber){
		super(deviceNumber);		
		panel = new PowerDistributionPanel();
		encoderZeroAdjust = super.getEncPosition();
	}
	public DCMotor(int deviceNumber, int ControlPeriodMs){
		super(deviceNumber,ControlPeriodMs);
		panel = new PowerDistributionPanel();
		encoderZeroAdjust = super.getEncPosition();
	}
	// this method takes in an input, clamps it, voltage references it, 
	//then clamps it and calls it on the motor
	@Override
	public void set(double outputValue){
		if(outputValue >= 1){
			outputValue = 1;
		}
		if(outputValue <=-1){
			outputValue = -1;
		}
		
		double voltage = panel.getVoltage();
		double reference = RobotStats.referenceVoltage / voltage;
		outputValue = outputValue * reference;
		
		if(outputValue >= 1){
			outputValue = 1;
		}
		if(outputValue <=-1){
			outputValue = -1;
		}
		super.set(outputValue);
		
	}
	@Override
	public int getEncPosition(){
		int currentPos = super.getEncPosition();
		return (currentPos - encoderZeroAdjust);
		
		
	}
	public void zeroEncoder(){
		encoderZeroAdjust = super.getEncPosition();
	}
	public void setStallCurrent(double current){
		stallCurrent= current;
	}
	public void setPercentStall(double percent){
		percentStall = percent;
	}
	public boolean checkStall(){
    	return stallCount > 25;
    	
	}
	public void updateStall(){
		if(this.getOutputCurrent() > percentStall*stallCurrent){
    		stallCount++;
    	}
    	else{
    		if(stallCount > 0){
    			stallCount--;
    		}
    	}
	}

}
