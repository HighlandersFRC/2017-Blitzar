package org.usfirst.frc.team4499.robot.commands;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

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
	boolean biggerThanMPB;
	CANTalon.MotionProfileStatus talonStatus;
	CANTalon.SetValueMotionProfile talonEnabledValue;
	CANTalon.SetValueMotionProfile talonDisabledValue;
	CANTalon.MotionProfileStatus MPStatus;
	
	class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {  
	    	MPTalon.processMotionProfileBuffer();
	    	// System.out.println("Motion profile buffer size " + MPStatus.btmBufferCnt + ", " + MPStatus.topBufferCnt + ", " + MPStatus.topBufferRem);
	    }
	}
	
	Notifier _notifier = new Notifier(new PeriodicRunnable());
	
    public OneDimensionalVelocityMP(double finalVelocity, CANTalon sensorTalon, Subsystem talonSubsystem, double numberOfTrajectoryPoints, double trajectoryPointDurationMS) {
    	requires(talonSubsystem);
    	
    	MPTalon = sensorTalon;
    	 // Store the initial velocity to construct the trajectory points
    	
    	goalVelocity = finalVelocity;
    	trajPointDurationMS = trajectoryPointDurationMS;
    	numTrajPoints = numberOfTrajectoryPoints;
    	talonEnabledValue = CANTalon.SetValueMotionProfile.Enable;
    	talonDisabledValue = CANTalon.SetValueMotionProfile.Disable;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	MPTalon.set(talonDisabledValue.value);
    	initialVelocity = MPTalon.getEncVelocity();
    	MPStatus = new CANTalon.MotionProfileStatus();
    	MPTalon.changeControlMode(TalonControlMode.MotionProfile);
    	MPTalon.clearMotionProfileTrajectories();
    	previousVelocity = MPTalon.getEncVelocity();
    	
    	
    	System.out.println("Initializing MP");
    	
    	startTime = Timer.getFPGATimestamp();
    	
    	
    	    	
    	MPTalon.setF(0.2);
    	MPTalon.setP(1);
    	MPTalon.setI(0.01);
    	MPTalon.setD(10);
    	// Calculate the number of points required for the MP
    	//System.out.println("goalVelocity " + goalVelocity);
    	//System.out.println("initialVelocity " + initialVelocity);
    	//System.out.println("trajPointDuration " + trajPointDurationMS);
    	//System.out.println("Calculated... " + (goalVelocity - initialVelocity) / (trajPointDurationMS / 1000 ));
    	//numTrajPoints = Math.abs((int) (Math.round( (goalVelocity - initialVelocity) / (trajPointDurationMS / 1000 ) )));
    	
    	System.out.println("numTrajPoints: " + numTrajPoints);
    	
    	if (MPStatus.hasUnderrun) {
    		System.out.println("Under-run");
    		MPTalon.clearMotionProfileHasUnderrun();
    	}
    	
    	// Talon SRX can store 128 trajectory points
    	if (numTrajPoints > 127) biggerThanMPB = true;
    	
    	//if (talonStatus.hasUnderrun) {
    		//System.out.println("Talon has underrun.");
    		//MPTalon.clearMotionProfileHasUnderrun();
    	//}

    	// Generate trajectory points and feed them to the Talon SRX's MPB
    	
    //	if (!biggerThanMPB) {
    	for (int i = 0; i < numTrajPoints; i++) {
    		
    		// Calculate the current trajectory point's velocity, and store it to calculate the next point's velocity
    		//System.out.println(trajPointDurationMS);
    		//System.out.println(RobotStats.maxLifterAccelerationLoad);
    		//System.out.println(trajPointDurationMS / 1000);
    		//System.out.println((trajPointDurationMS / 1000) * RobotStats.maxLifterAccelerationLoad);
    		//System.out.println("previousVelocity " + previousVelocity);
    		point.velocityOnly = true;
    		point.velocity = previousVelocity - ((trajPointDurationMS / 1000) * RobotStats.maxFlywheelAcceleration);
    		previousVelocity = previousVelocity - ((trajPointDurationMS / 1000) * RobotStats.maxFlywheelAcceleration);
    		point.profileSlotSelect = 0;
    		//point.velocity = previousVelocity;
    		//point.velocity = 3;
    		//previousVelocity = point.velocity;
    		
    		//point.timeDurMs = (int) trajPointDurationMS;
    		point.timeDurMs = 10;
    		point.profileSlotSelect = 0;
    		
    		
    		// If this is the first point, set the zeroPos value to true
    		point.zeroPos = false;
    		if (i == 0) {
    			point.zeroPos = true;
    			point.velocity = initialVelocity;
    		}
    		// If this is the last point, set the isLastPoint value to true
    		point.isLastPoint = false;
    		if ((i+1) == numTrajPoints) {
    			point.isLastPoint = true;
    		}
    		// Push the trajectory point to the Talon SRX's MPB
    		MPTalon.pushMotionProfileTrajectory(point);
    		
    		System.out.println("Trajectory point number " + i + " with a velocity of " + point.velocity + ", " + point.isLastPoint + ", " + point.zeroPos);
    		
		}
    	
    	MPTalon.changeMotionControlFramePeriod(5);
    	_notifier.startPeriodic(0.005);
/*    	
  		} else {
        	for (int i = 0; i < 128; i++) {
        		
        	}
        }
*/
    	
    	
    	//MPTalon.set(1);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	MPTalon.getMotionProfileStatus(MPStatus);
    	
    	//System.out.println("Executing MP, talon trying to output " + MPTalon.getOutputCurrent());
    	// System.out.println("Active point velocity " + MPStatus.activePoint.velocity + ", " + MPStatus.activePointValid);
    	// System.out.println("Active point is first " + MPStatus.activePoint.zeroPos);
    	// System.out.println("Active point is last " + MPStatus.activePoint.isLastPoint);
    	// System.out.println("Number of points in MPB " + MPStatus.btmBufferCnt);
    	
    	//MPTalon.set(talonEnabledValue.value);
    	// MPTalon.processMotionProfileBuffer();

    	if (MPStatus.btmBufferCnt > 5) {
    		MPTalon.set(CANTalon.SetValueMotionProfile.Enable.value);	
    		System.out.println("Enabled MP");
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
