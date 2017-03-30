package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.commands.DriveForward;
import org.usfirst.frc.team4499.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveForwardAuto extends CommandGroup {

    public DriveForwardAuto() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	//addSequential(new DriveForward(80));
    	addParallel(new SetGearIntake(-0.55, -0.12));
    //	addSequential(new Wait(0));
    	//addSequential(new DriveForward(68), 3);
    	//addSequential(new Turn(180, true), 3);
    	addSequential(new NavXDriveForward(0.28, 2.3));
    	addSequential(new Wait(0.2));
    	addParallel(new SetGearIntake(-0.4, 1));
    	addSequential(new Wait(0.5));
    	addParallel(new SetGearIntake(-0.4, 0));
    	addSequential(new Wait(1.5));
    		addSequential(new NavXDriveForward(-0.35, 0.6));
    	//addSequential(new DriveForward(68), 3);
    	addSequential(new SetGearIntake(0,0));
    	
    }
}
