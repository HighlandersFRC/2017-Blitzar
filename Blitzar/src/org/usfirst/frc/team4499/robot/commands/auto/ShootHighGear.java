package org.usfirst.frc.team4499.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4499.robot.commands.*;

/**
 *
 */
public class ShootHighGear extends CommandGroup {

    public ShootHighGear() {
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
    	
    	//Raise gear, start flywheel
    	addParallel(new SetGearIntake(-0.55, -0.12));
    	addParallel(new SetVortexPower(0));
    	addParallel(new SetReceiverPower(0));
    	addParallel(new SetAgitatorPower(0));
    	addParallel(new SetFlywheelVelocity(-4450));
    	addSequential(new Wait(1));
    	// Shoot for 3 seconds
    	//addParallel(new AutoAgitator());
    	addParallel(new SetVortexPower(-1));
    	addParallel(new SetReceiverPower(1));
    	addParallel(new SetAgitatorPower(1));
    	addSequential(new Wait(4));
    	addParallel(new SetFlywheelVelocity(0));
    	
    	// Stop firing
    	addParallel(new SetVortexPower(0));
    	addParallel(new SetReceiverPower(0));
    	addParallel(new SetAgitatorPower(0));
    	
    	// Drive forward to put down the gear
        addSequential(new NavXDriveForward(0.3, 2.2));
        
        // Wait a second, then eject the gear
        addSequential(new Wait(1));
        addParallel(new SetGearIntake(-0.4, 1));
        
        // Wait a second, then stop ejecting the gear
        addSequential(new Wait(1));
        addParallel(new SetGearIntake(-0.4, 0));
        
        // Wait 5 seconds and back up
        addSequential(new Wait(2));
        addSequential(new NavXDriveForward(-0.35, 0.6));
    	addSequential(new SetGearIntake(0,0));
    }
}
