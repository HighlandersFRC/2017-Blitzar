
package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
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

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.subsystems.*;

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

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	// Variable Declarations
	
	public static Flywheel flywheel = new Flywheel(); // Construct a flywheel subsystem object
	public static Receiver receiver = new Receiver(); // Construct a receiver subsystem object
	public static DriveTrain driveTrain = new DriveTrain(); // Construct a drive train object
	public static Turn turn = new Turn(90, true);
	public static ShootHigh shootHighAuto = new ShootHigh();
	public static DriveForward driveStraight = new DriveForward(36);
	
	public static float flyWheelPower = 0;
	public static float receiverPower = 0;
	public static float vortexPower = 0;
	
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
		//shootHighAuto.start();
		driveStraight.start();

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
		
		flyWheelPower = 0;
		receiverPower = 0;
		vortexPower = 0;
		
		RobotMap.flywheel.setP(0.05); // 0.05
		RobotMap.flywheel.setI(0.0001); //0.0005
		RobotMap.flywheel.setD(5); // 5
		
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
		
		RobotMap.turretMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		
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
			//flywheel.controlFlywheelPercentVBus();
		}
		
		// Control receiver
		if (oi.receiverSpeedIncrease.get() || oi.receiverSpeedDecrease.get()) {
			receiver.controlReceiver();
		}
		
		// Control turret
		if (oi.turretPanLeft.get()) {
			RobotMap.turretMotor.set(-0.1); // Left = negative
			System.out.println("Negative throttle");
		} else if (oi.turretPanRight.get()) {
			RobotMap.turretMotor.set(0.1); // Right = positive
			System.out.println("Positive throttle");
		} else {
			RobotMap.turretMotor.set(0);
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
		
		RobotMap.vortexMotorOne.set(-vortexPower);
		RobotMap.vortexMotorTwo.set(-vortexPower);
		
		// Prints twice so that one can be a progress bar, and the other can be a raw value
		//SmartDashboard.putNumber("Receiver power", -receiverPower);
		//SmartDashboard.putNumber( "Receiver power value", -receiverPower);
		//System.out.println("vortexPower " + vortexPower);
		
		
		
		
		//System.out.println("Flywheel speed " + RobotMap.flywheel.getEncVelocity());
		
		// (encvelocity / 4096) * 600 = rpm
		// Max speed is approximately 4880 RPM
		//System.out.println("Encoder speed in RPM: " + (RobotMap.flywheel.getEncVelocity() * 600) / 4096);
		//System.out.println("Flywheel target velocity: " + (RobotMap.flywheel.getSetpoint() * 600) / 4096);
		
		//System.out.println("Flywheel speed in RPM " + RobotMap.flywheel.getSpeed()); // RPM
		//System.out.println("Target speed in RPM " + flyWheelPower);
		//System.out.println("PID error in RPM: " + (RobotMap.flywheel.getClosedLoopError() * 600) / 4096);
		
		//System.out.println("PID error: " + RobotMap.flywheel.getClosedLoopError());
		//System.out.println(RobotMap.flywheel.GetIaccum());
		//System.out.println("Encoder speed (raw encoder): " + RobotMap.flywheel.getEncVelocity());
		
		//System.out.println("Turret position " + RobotMap.turretMotor.getPosition());
		
		SmartDashboard.putNumber("Flywheel speed", RobotMap.flywheel.getSpeed());
		
		System.out.println("Right encoder speed " + RobotMap.rightMotorOne.getSpeed());
		System.out.println("Left encoder speed " + RobotMap.leftMotorOne.getSpeed());
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
