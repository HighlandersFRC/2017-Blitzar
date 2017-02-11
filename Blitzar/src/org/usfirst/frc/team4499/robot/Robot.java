
package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.commands.auto.*;
import org.usfirst.frc.team4499.robot.subsystems.ExampleSubsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import java.io.IOException;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.subsystems.*;

import org.usfirst.frc.team4499.robot.tools.Tegra;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	Tegra tegra;
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	// Variable Declarations
	
	
	
	public static Flywheel flywheel = new Flywheel(); // Construct a flywheel subsystem object
	public static Receiver receiver = new Receiver(); // Construct a receiver subsystem object
	public static DriveTrain driveTrain = new DriveTrain(); // Construct a drive train subsystem object
	public static Turret turret = new Turret(); // Construct a new turret subsystem object
	public static Turn turn = new Turn(90, true);
	public static ShootHigh shootHighAuto = new ShootHigh();
	public static DriveForward driveStraight = new DriveForward(36); 
	public static TrackTargetPID trackTarget = new TrackTargetPID();
	public static NavXDriveForward gyroDriveForward = new NavXDriveForward(0.5, 2);
	
	public static float flyWheelPower = 0;
	public static float receiverPower = 0;
	public static float vortexPower = 0;
	public static float lifterPower = 0;
	public static boolean gotToPosition;
	double startTime;
	double previousVelocity;
	double previousTime;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	@Override
	public void robotInit() {
		
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		
		RobotMap.navx.zeroYaw();
		
		try {
			tegra = new Tegra();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		
		
		
		
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
		//turn.start();
		shootHighAuto.start();
		//driveStraight.start();
		//gyroDriveForward.start();

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		gotToPosition = true;
		startTime = Timer.getFPGATimestamp();
		previousVelocity = RobotMap.flywheel.getEncVelocity();
		
		flyWheelPower = 0;
		receiverPower = 0;
		vortexPower = 0;
		lifterPower = 0;
		
		RobotMap.flywheel.setP(0.05); // 0.05
		RobotMap.flywheel.setI(0.0001); //0.0005
		RobotMap.flywheel.setD(0.5); // 5
		
		// Max RPM is 4,800, max change in native units per 100ms is 13,140
		// 1023 / 13,140 = 0.078
		//RobotMap.flywheel.setF(0.078);
		RobotMap.flywheel.setF(0.01);
		
		RobotMap.rightMotorOne.setInverted(false);
		RobotMap.rightMotorTwo.setInverted(false);
		RobotMap.leftMotorOne.setInverted(false);
		RobotMap.leftMotorTwo.setInverted(false);
		
		RobotMap.flywheel.configNominalOutputVoltage(+0f, -0f);
		RobotMap.flywheel.configPeakOutputVoltage(+0f, -12f); // Only drive forward
		RobotMap.flywheel.set(0);
		RobotMap.flywheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		RobotMap.flywheel.reverseOutput(false);
		RobotMap.flywheel.reverseSensor(true);
		RobotMap.flywheel.setInverted(false);
		//RobotMap.flywheel.enableBrakeMode(false);
		
		RobotMap.receiverLeft.set(0);
		RobotMap.receiverRight.set(0);
		
		RobotMap.climbMotorOne.set(0);
		RobotMap.climbMotorTwo.set(0);
		
		RobotMap.turretMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		RobotMap.turretMotor.setEncPosition(0);
		RobotMap.turretMotor.setForwardSoftLimit(2.9);
		RobotMap.turretMotor.setReverseSoftLimit(-1.4);
		RobotMap.turretMotor.enableForwardSoftLimit(true);
		RobotMap.turretMotor.enableReverseSoftLimit(true);
		RobotMap.turretMotor.set(0);
		RobotMap.turretMotor.setPID(0.5, 0, 0); //try 0.6P and 50 D?
		RobotMap.turretMotor.setF(2.725); //2.725
		RobotMap.turretMotor.setVoltageRampRate(1000);
		
		driveTrain.controlDriveTrain();
		
		
		// Set flywheel for testing purposes
		RobotMap.flywheel.changeControlMode(CANTalon.TalonControlMode.Speed);
		//RobotMap.flywheel.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		//RobotMap.flywheel.set(-3950);
		//RobotMap.flywheel.set(-0.5);
		
		//flywheel.controlFlywheelVelocityMP(-10);
		
		
		//turn.start();
		//shootHighAuto.start();
		//driveStraight.start();
		
	//	trackTarget.start();
		
		//turret.controlTurretPositionRelative(0);
		//turret.controlTurretPositionRelative(0.5);
		
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		//System.out.println("shooter power: " + flyWheelPower);
		// Drive control
		/*if (Math.abs(oi.joystickOne.getRawAxis(1)) > 0.2) {
		RobotMap.leftMotorOne.set(-oi.joystickOne.getRawAxis(1)); // Up on joystick returns lower
		RobotMap.leftMotorTwo.set(-oi.joystickOne.getRawAxis(1)); // Negative -> Correct direction
		} else {
			RobotMap.leftMotorOne.set(0); 
			RobotMap.leftMotorTwo.set(0);
		}
		
		if (Math.abs(oi.joystickOne.getRawAxis(5)) > 0.2) {
		RobotMap.rightMotorOne.set(-oi.joystickOne.getRawAxis(5));
		RobotMap.rightMotorTwo.set(-oi.joystickOne.getRawAxis(5));
		} else {
			RobotMap.rightMotorOne.set(0); 
			RobotMap.rightMotorTwo.set(0);
		}*/
		
		//System.out.println(oi.joystickOne.getRawAxis(5));
		
		// Control flywheel
		if (oi.flyWheelSpeedIncrease.get() || oi.flyWheelSpeedDecrease.get()) {
			//flywheel.controlFlywheelVelocity();
			flywheel.controlFlywheelPercentVBus();
		}
		
		// Control receiver
		if (oi.receiverSpeedIncrease.get() || oi.receiverSpeedDecrease.get()) {
			receiver.controlReceiver();
		}
		
		// Control turret
		if (oi.turretPanLeft.get() || oi.turretPanRight.get()) {
			turret.controlTurretPower();
		}
		
		// Control vortex
		if (oi.vortexSpeedIncrease.get()) {
			vortexPower += 0.01;
		}
		if (oi.vortexSpeedDecrease.get()){
			vortexPower -= 0.01;
		}
		if (vortexPower > 1) {
			vortexPower = 1;
		}
		if (vortexPower < 0) {
			vortexPower = 0;
		}
		
		RobotMap.vortexMotor.set(-vortexPower);
		
		
		/*if (Tegra.x != -1) {
			//if (gotToPosition) {
				gotToPosition = false;
			System.out.println("Turret at " + RobotMap.turretMotor.getPosition() + " trying to turn " + Tegra.theta + " to " + (RobotMap.turretMotor.getPosition() + Tegra.theta));
		turret.controlTurretPositionRelative(RobotMap.turretMotor.getPosition() + Tegra.theta);
		//	}
		}*/
		//turret.controlTurretPositionRelative(0.5);
		//turret.controlTurretPositionRelative(OI.joystickOne.getRawAxis(5) * 1);
		// Control lifter
		/*if (oi.joystickOne.getPOV() == 90) {
			lifterPower += 0.02;
		}
		if (oi.joystickOne.getPOV() == 270 || oi.joystickOne.getPOV() == 315 || oi.joystickOne.getPOV() == 225){
			lifterPower -= 0.05;
		}
		if (lifterPower > 1) {
			lifterPower = 1;
		}
		if (lifterPower < 0) {
			lifterPower = 0;
		}*/
		
		//System.out.println("lifterPower " + lifterPower);
		
		//RobotMap.climbMotorOne.set(-lifterPower);
		//RobotMap.climbMotorTwo.set(lifterPower);
		//RobotMap.climbMotorOne.set(0); //negative
		//RobotMap.climbMotorTwo.set(0); //positive
	
		
		// Prints twice so that one can be a progress bar, and the other can be a raw value
		//SmartDashboard.putNumber("Receiver power", -receiverPower);
		//SmartDashboard.putNumber( "Receiver power value", -receiverPower);
		//System.out.println("vortexPower " + vortexPower);
		
		//System.out.println("Flywheel speed in RPM " + RobotMap.flywheel.getSpeed()); // RPM
		//System.out.println("Target speed in RPM " + flyWheelPower);
		//System.out.println("PID error in RPM: " + (RobotMap.flywheel.getClosedLoopError() * 600) / 4096);
		
		//System.out.println("PID error: " + RobotMap.flywheel.getClosedLoopError());
		//System.out.println(RobotMap.flywheel.GetIaccum());
		//System.out.println("Encoder speed (raw encoder): " + RobotMap.flywheel.getEncVelocity());
		
		//System.out.println("Turret position " + RobotMap.turretMotor.getPosition());
	//	System.out.println("Turret velocity " + RobotMap.turretMotor.getSpeed());
		
		System.out.println(RobotMap.navx.getYaw());
		
		
		SmartDashboard.putNumber("Turret position", RobotMap.turretMotor.getPosition());
		SmartDashboard.putNumber("Flywheel speed", RobotMap.flywheel.getSpeed());
		
		
		/*if (Math.abs(RobotMap.turretMotor.getSpeed()) > 55) {
    		System.out.println("Ended timer");
    		System.out.println("Timer: " + Timer.getFPGATimestamp());
    		System.out.println("Current acceleration: " + ((RobotMap.turretMotor.getSpeed() - previousVelocity) / (Timer.getFPGATimestamp() - previousTime)) );
    		System.out.println(startTime - Timer.getFPGATimestamp());
    	}
		
		previousVelocity = RobotMap.turretMotor.getSpeed();
    	previousTime = Timer.getFPGATimestamp();*/
		
		
		//System.out.println("Right encoder speed " + RobotMap.rightMotorOne.getSpeed());
		//System.out.println("Left encoder speed " + RobotMap.leftMotorOne.getSpeed());
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
