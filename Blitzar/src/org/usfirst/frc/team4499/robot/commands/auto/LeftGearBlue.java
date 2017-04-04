package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.commands.NavXDriveForward;
import org.usfirst.frc.team4499.robot.commands.NavXDriveForwardDistance;
import org.usfirst.frc.team4499.robot.commands.SetGearIntake;
import org.usfirst.frc.team4499.robot.commands.Turn;
import org.usfirst.frc.team4499.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftGearBlue extends CommandGroup {

    public LeftGearBlue() {
    	//addSequential(new DriveForward(80));
    	addParallel(new SetGearIntake(-0.55, -0.12));
    //	addSequential(new Wait(0));
    	//addSequential(new DriveForward(68), 3);
    	//addSequential(new Turn(180, true), 3);
    	addSequential(new NavXDriveForwardDistance(.25,87, true));
    	addSequential(new Wait(.2));
    	addSequential(new Turn(60,true), 2);
    	addSequential(new Wait(.1));
    	addSequential(new NavXDriveForwardDistance(.20, 15, false));
    	addSequential(new Wait(0.2));
    	addParallel(new SetGearIntake(-0.4, 1));
    	addSequential(new Wait(0.5));
    	addParallel(new SetGearIntake(-0.4, 0));
    	addSequential(new Wait(1.5));
    		addSequential(new NavXDriveForwardDistance(-0.35, 6, false));
    	//addSequential(new DriveForward(68), 3);
    	addSequential(new SetGearIntake(0,0));
    }
}
