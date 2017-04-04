package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.commands.NavXDriveForwardDistance;
import org.usfirst.frc.team4499.robot.commands.SetGearIntake;
import org.usfirst.frc.team4499.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveForwardAuto extends CommandGroup {

    public DriveForwardAuto() {
    	addParallel(new SetGearIntake(-0.52, -0.12));
        addSequential(new NavXDriveForwardDistance(.3, 90, true));
        addSequential(new Wait(3));
        addParallel(new SetGearIntake(0, -0.12));
    }
}
