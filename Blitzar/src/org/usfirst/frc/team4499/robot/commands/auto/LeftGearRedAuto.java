package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftGearRedAuto extends CommandGroup {

	//DROPS OF A GEAR ON THE LEFT PEG FOR THE RED ALLIANCE
    public LeftGearRedAuto() {
     	//addSequential(new DriveForward(80));
    	addParallel(new SetGearIntake(-0.52, -0.12));
    //	addSequential(new Wait(0));
    	//addSequential(new DriveForward(68), 3);
    	//addSequential(new Turn(180, true), 3);
    	addSequential(new NavXDriveForwardDistance(.25,85));
    	addSequential(new Wait(.2));
    	addSequential(new Turn(60,true), 2);
    	addSequential(new Wait(.1));
    	addSequential(new NavXDriveForwardDistance(.25, 15));
    	addSequential(new Wait(0.2));
    	addParallel(new SetGearIntake(-0.4, 1));
    	addSequential(new Wait(0.5));
    	addParallel(new SetGearIntake(-0.4, 0));
    	addSequential(new Wait(1.5));
    		addSequential(new NavXDriveForwardDistance(-0.35, 6));
    	//addSequential(new DriveForward(68), 3);
    	addSequential(new SetGearIntake(0,0));
    	
    }
}
