package org.usfirst.frc.team4499.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.subsystems.*;

/**
 *
 */
public class ShootHigh extends CommandGroup {

	Flywheel flywheel = new Flywheel();
	
    public ShootHigh() {
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
    	
    	addParallel(new setFlywheelVelocity(-3400));
    	addSequential(new Wait(1));
    	addParallel(new setVortexPower(-1));
    	addParallel(new setReceiverPower(-1));
    	addSequential(new Wait(3));
    	
    	addParallel(new setFlywheelVelocity(0));
    	addParallel(new setVortexPower(0));
    	addParallel(new setReceiverPower(0));
    	
    	addSequential(new DriveForward(85));
    	addSequential(new Wait(0.5));
    	addSequential(new Turn(90, false));
    	addSequential(new Wait(0.5));
    	addSequential(new NavXDriveForward(0.4, 2));
    	
    	addParallel(new setFlywheelVelocity(-4000));
    	addSequential(new Wait(1));
    	addParallel(new setVortexPower(-1));
    	addParallel(new setReceiverPower(-1));
    	
    }
}
