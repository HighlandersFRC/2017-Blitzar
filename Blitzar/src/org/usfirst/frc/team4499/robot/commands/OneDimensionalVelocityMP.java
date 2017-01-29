package org.usfirst.frc.team4499.robot.commands;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.ArrayDeque;
import java.util.Queue;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.RobotStats;

public class OneDimensionalVelocityMP extends Command {

	CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
	CANTalon MPTalon;
	
	double previousVelocity;
	double initialVelocity;
	double goalVelocity;
	double startTime;
	double previousTime;
	double trajPointDurationMS;
	double numTrajPoints;
	double maxVelocity;
	float maxAcceleration;
	boolean biggerThanMPB;
	Queue <CANTalon.TrajectoryPoint> trajPointQueue;
	CANTalon.MotionProfileStatus talonStatus;
	CANTalon.SetValueMotionProfile talonEnabledValue;
	CANTalon.SetValueMotionProfile talonDisabledValue;
	CANTalon.MotionProfileStatus MPStatus = new CANTalon.MotionProfileStatus();
	
	
	// Periodic Runnable
	class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {
	    	
	    	MPTalon.getMotionProfileStatus(MPStatus);
	    	if (MPStatus.btmBufferCnt < 120 && MPStatus.topBufferCnt > 0) {
	    	//System.out.println("Processing MPB");
	    	MPTalon.processMotionProfileBuffer();
	    	}
	    	if (MPStatus.topBufferCnt < 2000 && !trajPointQueue.isEmpty()){ 
	    	MPTalon.pushMotionProfileTrajectory(trajPointQueue.remove());
	    	}
	    	
	    	// System.out.println("Motion profile buffer size " + MPStatus.btmBufferCnt + ", " + MPStatus.topBufferCnt + ", " + MPStatus.topBufferRem);
	    }
	}
	
	Notifier _notifier = new Notifier(new PeriodicRunnable());
	
    public OneDimensionalVelocityMP(double finalVelocity, CANTalon sensorTalon, Subsystem talonSubsystem) {
    	requires(talonSubsystem);
    	
    	
    	maxVelocity = -60;
    	MPTalon = sensorTalon;
    	 // Store the initial velocity to construct the trajectory points
    	
    	goalVelocity = finalVelocity;
    	if (goalVelocity > maxVelocity) {
    		goalVelocity = maxVelocity;
    	}
    	talonEnabledValue = CANTalon.SetValueMotionProfile.Enable;
    	talonDisabledValue = CANTalon.SetValueMotionProfile.Disable;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	
    	MPTalon.clearMotionProfileTrajectories();
    	
    	maxAcceleration = -60;
    	trajPointQueue = new ArrayDeque <CANTalon.TrajectoryPoint>();
    	MPTalon.set(talonDisabledValue.value);
    	initialVelocity = MPTalon.getEncVelocity();
    	MPStatus = new CANTalon.MotionProfileStatus();
    	MPTalon.changeControlMode(TalonControlMode.MotionProfile);
    	MPTalon.clearMotionProfileTrajectories();
    	initialVelocity = MPTalon.getSpeed();
    	
    	System.out.println("Initializing MP");
    	
    	startTime = Timer.getFPGATimestamp();
    	    	
    	//MPTalon.setF(0.2);
    	//MPTalon.setP(1);
    	//MPTalon.setI(0.01);
    	//MPTalon.setD(10);
    	// Calculate the number of points required for the MP
    	//System.out.println("goalVelocity " + goalVelocity);
    	//System.out.println("initialVelocity " + initialVelocity);
    	//System.out.println("trajPointDuration " + trajPointDurationMS);
    	//System.out.println("Calculated... " + (goalVelocity - initialVelocity) / (trajPointDurationMS / 1000 ));
    	//numTrajPoints = Math.abs((int) (Math.round( (goalVelocity - initialVelocity) / (trajPointDurationMS / 1000 ) )));
    	
//    	System.out.println("numTrajPoints: " + numTrajPoints);
    	
    	if (MPStatus.hasUnderrun) {
    		System.out.println("Under-run");
    		MPTalon.clearMotionProfileHasUnderrun();
    	}
    	
    	//if (talonStatus.hasUnderrun) {
    		//System.out.println("Talon has underrun.");
    		//MPTalon.clearMotionProfileHasUnderrun();
    	//}

    	System.out.println("finalVelocity: " + goalVelocity);
    	System.out.println("maxAcceleration: " + maxAcceleration);
    	System.out.println("initialVelocity: " + initialVelocity);
    	
    	for (double v = initialVelocity; v > goalVelocity; v += (maxAcceleration * 0.01) ) {
    		point.velocityOnly = true;
    		point.velocity = v;
    		point.profileSlotSelect = 0;
    		point.timeDurMs = 10;
    		
    		System.out.println("v " + v);
    		
    		// If this is the first point, set the zeroPos value to true
    		point.zeroPos = false;
    		if (v == initialVelocity) {
    			point.zeroPos = true;
    			point.velocity = initialVelocity;
    		}
    		// If this is the last point, set the isLastPoint value to true
    		point.isLastPoint = false;
    		if (v + maxAcceleration * 0.01 > goalVelocity) {
    			point.isLastPoint = true;
    		}
    		
    		//System.out.println("Calculated velocity: " + point.velocity);
    		// Push the trajectory point to the queue
    		// The notifier then pushes from the queue to the Talon SRX's MPB
    		//System.out.println(trajPointQueue.offer(point));
    		trajPointQueue.offer(point);
    		//System.out.println("Added point with velocity: " + point.velocity);
    		
		}
    	MPTalon.getMotionProfileStatus(MPStatus); // Grab status for the notifier
    	
    	for (int i = 0; i < trajPointQueue.size(); i++) {
    		System.out.println("Item " + i + " with velocity of " + trajPointQueue.remove().velocity);
    	}
    	
    	MPTalon.changeMotionControlFramePeriod(5);
    	_notifier.startPeriodic(0.005);
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	MPTalon.getMotionProfileStatus(MPStatus);
    	
    	// System.out.println("Executing MP, talon trying to output " + MPTalon.getOutputCurrent());
    	//System.out.println("Active point velocity " + MPStatus.activePoint.velocity + ", " + MPStatus.activePointValid);
    	//System.out.println("Active point is first " + MPStatus.activePoint.zeroPos);
    	//System.out.println("Active point is last " + MPStatus.activePoint.isLastPoint);
    	//System.out.println("Number of points in MPB " + MPStatus.btmBufferCnt);
    	//System.out.println("Number of points in API buffer " + MPStatus.topBufferCnt);
    	
    	//MPTalon.set(talonEnabledValue.value);

    	if (MPStatus.btmBufferCnt > 5) {
    		MPTalon.set(CANTalon.SetValueMotionProfile.Enable.value);	
    		//System.out.println("Enabled MP");
    	}
    	
    	System.out.flush();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return Math.abs(goalVelocity - MPTalon.getEncVelocity()) < 5;
       	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	_notifier.stop();
    	System.out.println("Finished motion profile");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	_notifier.stop();
    }
}
	