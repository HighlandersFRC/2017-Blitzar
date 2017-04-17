package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.commands.NavXDriveForwardDistance;
import org.usfirst.frc.team4499.robot.commands.SetAgitatorPower;
import org.usfirst.frc.team4499.robot.commands.SetFlywheelVelocity;
import org.usfirst.frc.team4499.robot.commands.SetGearIntake;
import org.usfirst.frc.team4499.robot.commands.SetReceiverPower;
import org.usfirst.frc.team4499.robot.commands.SetVortexPower;
import org.usfirst.frc.team4499.robot.commands.TurretMP;
import org.usfirst.frc.team4499.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleGearBlue extends CommandGroup {

    public MiddleGearBlue() {
    	addParallel(new SetGearIntake(-0.55, -0.12));
    	
    	addParallel(new SetFlywheelVelocity(-4250));
    	addSequential(new TurretMP(-3.2));
    	
    	addParallel(new SetVortexPower(-.8f));
    	addParallel(new SetReceiverPower(1));
    	addParallel(new SetAgitatorPower(1));
    	addSequential(new Wait(4.5));
    	addParallel(new SetVortexPower(0));
    	addParallel(new SetReceiverPower(0));
    	addParallel(new SetAgitatorPower(0));
    	addParallel(new SetFlywheelVelocity(0));
    	
    	
    	addSequential(new NavXDriveForwardDistance(.15, 115, true), 4);
    	addSequential(new Wait(0.2));
    	addParallel(new SetGearIntake(-0.4, 1));
    	addSequential(new Wait(0.5));
    	addParallel(new SetGearIntake(-0.4, 0));
    	addSequential(new Wait(1.5));
    	addSequential(new NavXDriveForwardDistance(-0.2, 10, true));
    	addSequential(new SetGearIntake(0,0));
    }
}
