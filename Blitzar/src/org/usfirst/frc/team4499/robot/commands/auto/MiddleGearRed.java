package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleGearRed extends CommandGroup {

    public MiddleGearRed() {
        
    	//addSequential(new DriveForward(80));
    	addParallel(new SetGearIntake(-0.55, -0.12));
    	addParallel(new SetFlywheelVelocity(-4250));
    	//addParallel(new TrackTargetPID());
    	//addParallel(new AutoFlywheelSpeed());
    	addSequential(new Wait(1));
    	addParallel(new SetVortexPower(-.75f));
    	addParallel(new SetReceiverPower(.75f));
    	addParallel(new SetAgitatorPower(1));
    	addSequential(new Wait(4.5));
    	addParallel(new SetVortexPower(0));
    	addParallel(new SetReceiverPower(0));
    	addParallel(new SetAgitatorPower(0));
    	addParallel(new SetFlywheelVelocity(0));
    	//addSequential(new DriveForward(68), 3);
    	//addSequential(new Turn(180, true), 3);
    	addSequential(new NavXDriveForwardDistance(.2, 115, true), 4);
    	addSequential(new Wait(0.2));
    	addParallel(new SetGearIntake(-0.4, 1));
    	addSequential(new Wait(0.5));
    	addParallel(new SetGearIntake(-0.4, 0));
    	addSequential(new Wait(1.5));
    		addSequential(new NavXDriveForwardDistance(-0.28, 10, true));
    	//addSequential(new DriveForward(68), 3);
    	addSequential(new SetGearIntake(0,0));
    	
    }
}
