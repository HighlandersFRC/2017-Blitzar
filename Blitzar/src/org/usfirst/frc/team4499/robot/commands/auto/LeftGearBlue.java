package org.usfirst.frc.team4499.robot.commands.auto;

import org.usfirst.frc.team4499.robot.commands.NavXDriveForward;
import org.usfirst.frc.team4499.robot.commands.NavXDriveForwardDistance;
import org.usfirst.frc.team4499.robot.commands.SetAgitatorPower;
import org.usfirst.frc.team4499.robot.commands.SetFlywheelVelocity;
import org.usfirst.frc.team4499.robot.commands.SetGearIntake;
import org.usfirst.frc.team4499.robot.commands.SetReceiverPower;
import org.usfirst.frc.team4499.robot.commands.SetTalonBrakeCoast;
import org.usfirst.frc.team4499.robot.commands.SetVortexPower;
import org.usfirst.frc.team4499.robot.commands.Turn;
import org.usfirst.frc.team4499.robot.commands.TurretMP;
import org.usfirst.frc.team4499.robot.commands.Wait;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.commands.ForwardCorrection;

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
    	
    	addParallel(new SetFlywheelVelocity(-3500));
    	addSequential(new TurretMP(-3.28),2);
    	
    	addParallel(new SetVortexPower(-.8f));
    	addParallel(new SetReceiverPower(1));
    	addParallel(new SetAgitatorPower(1));
    	
    //	RobotMap.climbMotorOne.set(1);
	//	RobotMap.climbMotorTwo.set(1);
    	
		addSequential(new Wait(4));
		addParallel(new TurretMP(0));
		addParallel(new SetVortexPower(0));
    	addParallel(new SetReceiverPower(0));
    	addParallel(new SetAgitatorPower(0));
    	addParallel(new SetFlywheelVelocity(0));
    	
    	addSequential(new NavXDriveForwardDistance(.2, 114, true));
    	addSequential(new Wait(.2));
    	addSequential(new Turn(60,true), 3);
    	//addSequential(new Wait(.1));
    	addSequential(new ForwardCorrection(.20, 2,60, false));
    	addSequential(new Wait(0.2));
    	addParallel(new SetTalonBrakeCoast(RobotMap.gearIntakeRoller, false));
    	addSequential(new Wait(15));
    	addParallel(new SetTalonBrakeCoast(RobotMap.gearIntakeRoller, true));
    	/*
    	addParallel(new SetGearIntake(-0.4, 1));
    	addSequential(new Wait(0.5));
    	addParallel(new SetGearIntake(-0.4, 0));
    	addSequential(new Wait(1.5));
    	*/
    		//addSequential(new NavXDriveForwardDistance(-0.35, 6, false));
    	//addSequential(new DriveForward(68), 3);
    	addSequential(new SetGearIntake(0,0));
    }
}
