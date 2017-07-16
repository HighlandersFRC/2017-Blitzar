package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.commands.AutoFlywheelSpeed;
import org.usfirst.frc.team4499.robot.commands.DriveForward;
import org.usfirst.frc.team4499.robot.commands.NavXDriveForward;
import org.usfirst.frc.team4499.robot.commands.SetAgitatorPower;
import org.usfirst.frc.team4499.robot.commands.TrackTargetPID;
import org.usfirst.frc.team4499.robot.commands.Turn;
import org.usfirst.frc.team4499.robot.commands.Wait;
import org.usfirst.frc.team4499.robot.commands.SetFlywheelVelocity;
import org.usfirst.frc.team4499.robot.commands.SetGearIntake;
import org.usfirst.frc.team4499.robot.commands.SetReceiverPower;
import org.usfirst.frc.team4499.robot.commands.SetVortexPower;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootHighRedSide extends CommandGroup {

    public ShootHighRedSide() {
    	// Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require0 all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	
    	addParallel(new SetGearIntake(-0.6, -0.12));
    	// FIRE AT THE START
    	//addSequential(new SetFlywheelVelocity(-3100));
    	
    	addParallel(new SetVortexPower(0));
    	addParallel(new SetReceiverPower(0));
    	addParallel(new SetAgitatorPower(0));
    	addParallel(new SetFlywheelVelocity(-3610)); //-3412
    	//addParallel(new SetVortexPower(0));
    	//addParallel(new SetReceiverPower(0));
    	//addParallel(new SetAgitatorPower(0));
    	addSequential(new Wait(2));
    	addParallel(new SetVortexPower(-1));
    	addParallel(new SetReceiverPower(1));
    	addParallel(new SetAgitatorPower(1));
    	addSequential(new Wait(5));
    	addParallel(new SetVortexPower(1));
    	addParallel(new SetReceiverPower(-1));
    	addParallel(new SetAgitatorPower(-1));
    	addSequential(new Wait(1));
    	addParallel(new SetVortexPower(-1));
    	addParallel(new SetReceiverPower(1));
    	addParallel(new SetAgitatorPower(1));
    	addSequential(new Wait(2));
    	addParallel(new SetVortexPower(0));
    	addParallel(new SetReceiverPower(0));
    	addParallel(new SetAgitatorPower(0));
    	addParallel(new SetFlywheelVelocity(0));
    	addSequential(new NavXDriveForward(0.5, 1.5));
    	
    	/*
    	//addParallel(new SetFlywheelVelocity(-3412));
    	//addParallel(new Wait(2));
    	addParallel(new SetFlywheelVelocity(0));
    	addParallel(new SetVortexPower(0));
    	addParallel(new SetReceiverPower(0));
    	addParallel(new SetAgitatorPower(0));
    	
    	addSequential(new DriveForward(75.5)); //7.25 inches past hopper is 78.5 inches from wall
    	addSequential(new Wait(0.5));
    	addSequential(new Turn(90, true));
    	addSequential(new Wait(0.5));
    	
    	addSequential(new DriveForward(40), 2);
    	addSequential(new NavXDriveForward(0.2, 1));
    	
    	//addParallel(new setFlywheelVelocity(-4000));
    	addParallel(new TrackTargetPID());
    	addParallel(new AutoFlywheelSpeed());
    	
    	addSequential(new Wait(1));
    	addParallel(new SetVortexPower(-1));
    	addParallel(new SetReceiverPower(-1));
    	addParallel(new SetAgitatorPower(1));
    	*/
    }
}
