package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.commands.NavXDriveForwardDistance;
import org.usfirst.frc.team4499.robot.commands.SetGearIntake;
import org.usfirst.frc.team4499.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleGearBlue extends CommandGroup {

    public MiddleGearBlue() {
    	addParallel(new SetGearIntake(-0.55, -0.12));
    	addSequential(new NavXDriveForwardDistance(.28, 76, true));
    	addSequential(new Wait(0.2));
    	addParallel(new SetGearIntake(-0.4, 1));
    	addSequential(new Wait(0.5));
    	addParallel(new SetGearIntake(-0.4, 0));
    	addSequential(new Wait(1.5));
    	addSequential(new NavXDriveForwardDistance(-0.28, 10, true));
    	addSequential(new SetGearIntake(0,0));
    }
}
