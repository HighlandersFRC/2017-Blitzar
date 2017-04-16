package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.commands.NavXDriveForwardDistance;
import org.usfirst.frc.team4499.robot.commands.SetAgitatorPower;
import org.usfirst.frc.team4499.robot.commands.SetFlywheelVelocity;
import org.usfirst.frc.team4499.robot.commands.SetGearIntake;
import org.usfirst.frc.team4499.robot.commands.SetReceiverPower;
import org.usfirst.frc.team4499.robot.commands.SetVortexPower;
import org.usfirst.frc.team4499.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootBlue extends CommandGroup {

    public ShootBlue() {
    	//addSequential(new DriveForward(80));
    	addParallel(new SetGearIntake(-0.52, -0.12));
    	addParallel(new SetFlywheelVelocity(-3720));
    	addSequential(new Wait(1));
    	for(int i=0; i<8;i++){
    	addParallel(new SetVortexPower(-.5f));
    	addParallel(new SetReceiverPower(1));
    	addParallel(new SetAgitatorPower(1));
    	addSequential(new Wait(.5));
    	
      	addParallel(new SetVortexPower(-.5f));
    	addParallel(new SetReceiverPower(1));
    	addParallel(new SetAgitatorPower(-1));
    	addSequential (new Wait(.5));
    	
    	}
    	addParallel(new SetVortexPower(0));
    	addParallel(new SetReceiverPower(0));
    	addParallel(new SetAgitatorPower(0));
    	addParallel(new SetFlywheelVelocity(0));
    	//addSequential(new DriveForward(80));
    	addParallel(new SetGearIntake(-0.55, -0.12));
    //	addSequential(new Wait(0));
    	//addSequential(new DriveForward(68), 3);
    	//addSequential(new Turn(180, true), 3);
    	addSequential(new NavXDriveForwardDistance(-.2,113, true));
    }
}
