package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightGearRedAuto extends CommandGroup {

    public RightGearRedAuto() {
    	//addSequential(new DriveForward(80));
    	addParallel(new SetGearIntake(-0.52, -0.12));
    	
		
	
    	addParallel(new SetFlywheelVelocity(-3770));
    	addSequential(new Wait(.5));
    //	for(int i=0; i<6;i++){
    	addParallel(new SetVortexPower(-.8f));
    	addParallel(new SetReceiverPower(1));
    	addParallel(new SetAgitatorPower(1));
    	addSequential(new Wait(3));
    
    
      	addParallel(new SetVortexPower(0));
    	addParallel(new SetReceiverPower(0));
    	addParallel(new SetAgitatorPower(0));
    	addParallel(new SetFlywheelVelocity(0));
    	
    	//addSequential(new DriveForward(80));
    	addParallel(new SetGearIntake(-0.55, -0.12));
    //	addSequential(new Wait(0));
    	//addSequential(new DriveForward(68), 3);
    	//addSequential(new Turn(180, true), 3);	
    	addSequential(new NavXDriveForwardDistance(.2,107, true));
    	addSequential(new Wait(.2));
    	addSequential(new Turn(-60,true), 1);
    	//addSequential(new Wait(.1));
    	addSequential(new ForwardCorrection(.20, 2,-60, false));
    	addParallel(new SetGearIntake(-0.4, 1));
    	addSequential(new Wait(0.5));
    	addParallel(new SetGearIntake(-0.4, 0));
    	addSequential(new Wait(1.5));
    		addSequential(new NavXDriveForwardDistance(-0.35, 6, false));
    	//addSequential(new DriveForward(68), 3);
    	addSequential(new SetGearIntake(0,0));
    }
}