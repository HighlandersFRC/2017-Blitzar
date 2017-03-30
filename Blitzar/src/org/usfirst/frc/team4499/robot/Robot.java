
package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.commands.auto.*;
import org.usfirst.frc.team4499.robot.subsystems.ExampleSubsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import java.io.IOException;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.subsystems.*;
import org.usfirst.frc.team4499.robot.tools.CMDGroup;
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
	//SendableChooser<Command> chooser = new SendableChooser<>();
	CMDGroup autoGroup = new CMDGroup();
	
	// Variable Declarations
	
	
	// Initialize subsystems
	public static Flywheel flywheel = new Flywheel();
	public static Receiver receiver = new Receiver();
	public static Vortex vortex = new Vortex();
	public static DriveTrain driveTrain = new DriveTrain();
	public static Turret turret = new Turret();
	public static Agitator agitator = new Agitator();
	public static GearIntake gearIntake = new GearIntake();
	
	
	
	public static Turn turn = new Turn(90, false);
	public static ShootHigh shootHighAuto = new ShootHigh();
	public static DriveForward driveStraight = new DriveForward(75.5); 
	public static TrackTargetPID trackTarget = new TrackTargetPID();
	public static AutoFlywheelSpeed autoFlywheelSpeed = new AutoFlywheelSpeed();
	public static NavXDriveForward gyroDriveForward = new NavXDriveForward(0.5, 2);
	public static SetVortexPower stopVortex = new SetVortexPower(0);
	public static SetReceiverPower stopReceiver = new SetReceiverPower(0);
	public static SetAgitatorPower stopAgitator = new SetAgitatorPower(0);
	public static SetDriveTrainCurrent setDriveTrainCurrent = new SetDriveTrainCurrent(10);
	public static ControlGearIntake controlGearIntake = new ControlGearIntake(true);
	
	public static float flywheelMasterPTerm;
	public static float flywheelMasterITerm;
	public static float flywheelMasterDTerm;
	public static float flywheelMasterIZone;
	
	public static float flyWheelPower = 0;
	public static float receiverPower = 0;
	public static float vortexPower = 0;
	public static float lifterPower = 0;
	public static float turretPower = 0;
	public static boolean gotToPosition;
	public static boolean gearPistonOut = false;
	public static boolean basinPistonOut = false;
	public static float tempMaxCurrent = 0;
	public static int fireCount = 0;
	public static boolean fireEnabled;
	
	double startTime;
	double previousVelocity;
	double previousTime;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		
		
		oi = new OI();
		//chooser.addDefault("Default Auto", new ExampleCommand());
	//	chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", chooser);
		
		RobotMap.gearIntakeRotate.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		RobotMap.gearIntakeRotate.setPosition(0);
		
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
		//stopVortex.start();
		//stopReceiver.start();
		//stopAgitator.start();
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
		//autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		 
		RobotMap.flywheelMaster.configNominalOutputVoltage(+0f, -0f);
		RobotMap.flywheelMaster.configPeakOutputVoltage(+1f, -12f); // Only drive forward
		RobotMap.flywheelMaster.set(0);
		RobotMap.flywheelMaster.setPosition(0);
		RobotMap.flywheelMaster.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		RobotMap.flywheelMaster.reverseOutput(false);
		RobotMap.flywheelMaster.reverseSensor(true);
		RobotMap.flywheelMaster.setInverted(false);
		//RobotMap.flywheelMaster.enableBrakeMode(false);
		RobotMap.flywheelSlave.changeControlMode(TalonControlMode.Follower);
		RobotMap.flywheelSlave.set(5);
		RobotMap.flywheelMaster.setF(0.02141);
		RobotMap.flywheelMaster.setP(0.1);
		RobotMap.flywheelMaster.setI(0); //0.00022
		RobotMap.flywheelMaster.setD(0.5); // 1.3	
		
	
		RobotMap.navx.zeroYaw();
		if (AutoChooser.getAuto() != null) {
    	CommandGroup autonomous = AutoChooser.getAuto();
    	autonomous.start();
		}
    	//autoGroup.add(autonomous);
	
		//stopVortex.start();
		//stopReceiver.start();
		//stopAgitator.start();
		
//		turn.start();
		//shootHighAuto.start();
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
		//SmartDashboard.putNumber("Flywheel velocity", RobotMap.flywheelMaster.getSpeed());
		//System.out.println(RobotMap.flywheelMaster.getSpeed());
		//SmartDashboard.putNumber("NavX Yaw in auto " , RobotMap.navx.getYaw());
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		//stopVortex.start();
	//	stopReceiver.start();
//		stopAgitator.start();
		
		flywheel.controlFlywheelVelocity();
		gotToPosition = true;
		startTime = Timer.getFPGATimestamp();
		previousVelocity = RobotMap.flywheelMaster.getEncVelocity();
		
		flyWheelPower = 0;
		receiverPower = 0;
		vortexPower = 0;
		lifterPower = 0;
		
		RobotMap.flywheelMaster.setP(0.1); //0,07 for comp
		RobotMap.flywheelMaster.setI(0); // 0.001 for comp
		RobotMap.flywheelMaster.setD(0.5); // 1.5
		RobotMap.flywheelMaster.setIZone(300);
		
		RobotMap.navx.zeroYaw();
		
		// Max RPM is 4,800, max change in native units per 100ms is 13,140
		// 1023 / 13,140 = 0.078
		//RobotMap.flywheel.setF(0.078);
		RobotMap.flywheelMaster.setF(0.0235); //0.03122 0.015 COMP BOT
		
		RobotMap.rightMotorOne.setInverted(false);
		RobotMap.rightMotorTwo.setInverted(false);
		RobotMap.leftMotorOne.setInverted(false);
		RobotMap.leftMotorTwo.setInverted(false);
		
		RobotMap.rightMotorTwo.reverseSensor(true);
		RobotMap.leftMotorOne.reverseSensor(true);
		RobotMap.rightMotorOne.reverseSensor(true);
		
		RobotMap.rightMotorOne.setCurrentLimit(10);
		RobotMap.rightMotorTwo.setCurrentLimit(10);
		RobotMap.leftMotorOne.setCurrentLimit(10);
		RobotMap.leftMotorTwo.setCurrentLimit(10);
		RobotMap.rightMotorOne.setVoltageRampRate(9999);
		RobotMap.rightMotorTwo.setVoltageRampRate(9999);
		RobotMap.leftMotorOne.setVoltageRampRate(9999);
		RobotMap.leftMotorTwo.setVoltageRampRate(9999);
		

		
		RobotMap.rightMotorOne.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		RobotMap.leftMotorOne.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		RobotMap.rightMotorTwo.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		RobotMap.leftMotorTwo.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		
		RobotMap.flywheelMaster.configNominalOutputVoltage(+0f, -0f);
		RobotMap.flywheelMaster.configPeakOutputVoltage(+1f, -12f); // Only drive forward
		RobotMap.flywheelMaster.set(0);
		RobotMap.flywheelMaster.setPosition(0);
		RobotMap.flywheelMaster.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		RobotMap.flywheelMaster.reverseOutput(false);
		RobotMap.flywheelMaster.reverseSensor(true);
		RobotMap.flywheelMaster.setInverted(false);
		//RobotMap.flywheelMaster.enableBrakeMode(false);
		RobotMap.flywheelSlave.changeControlMode(TalonControlMode.Follower);
		RobotMap.flywheelSlave.set(5);
		
		RobotMap.receiverRight.set(0);
		
		
	
		
		RobotMap.climbMotorOne.set(0);
		RobotMap.climbMotorTwo.set(0);
		
		RobotMap.turretMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		RobotMap.turretMotor.setEncPosition(0);
		RobotMap.turretMotor.setForwardSoftLimit(0.65);
		RobotMap.turretMotor.setReverseSoftLimit(-1.5);
		RobotMap.turretMotor.enableForwardSoftLimit(true);
		RobotMap.turretMotor.enableReverseSoftLimit(true);
		RobotMap.turretMotor.set(0);
		RobotMap.turretMotor.setPID(0.5, 0, 0); //try 0.6P and 50 D?
		RobotMap.turretMotor.setF(2.725); //2.725
		RobotMap.turretMotor.setVoltageRampRate(1000);
		
		RobotMap.agitatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		
		driveTrain.controlDriveTrain();
		controlGearIntake.start();
	//	if(OI.gearRoller.get())
	//	{
	//		RobotMap.gearIntakeRoller.set(-0.8); 
	//	}
	//	else
	//	{
	//		RobotMap.gearIntakeRoller.set(0);
	//	}
		
		//setDriveTrainCurrent.start();
		// Set flywheel for testing purposes
		//RobotMap.flywheelMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
		//RobotMap.flywheelMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		//RobotMap.flywheelMaster.set(-3000);
		//RobotMap.flywheelMaster.set(-0.5);
		
		
		
		
	//	flywheel.controlFlywheelVelocityMP(3600);
		//flywheelMaster.controlFlywheelVelocityMP(-10);
		
		
		//turn.start();
		//shootHighAuto.start();
		//driveStraight.start();
		
		// Turret track
	//	trackTarget.start();
		
		// Auto flywheel speed set
		//autoFlywheelSpeed.start();
		
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
//	System.out.println("fire count " + fireCount);
		//System.out.println("Position of gear rotate: " + RobotMap.gearIntakeRotate.getPosition() + "roller pos " + RobotMap.gearIntakeRoller.getPosition());
		// Control flywheel
	//System.out.println("turret pos " + RobotMap.turretMotor.getPosition());
		if (oi.flyWheelSpeedIncrease.get() || oi.flyWheelSpeedDecrease.get()) {
			flywheel.controlFlywheelVelocity();
			//flywheel.controlFlywheelPercentVBus();
		}
		//System.out.println(RobotMap.turretMotor.getPosition());
		/*
		// Control receiver
		if (oi.receiverSpeedIncrease.get() || oi.receiverSpeedDecrease.get()) {
			receiver.controlReceiver();
		}
		*/
		
		// Control vortex
		/*
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
		RobotMap.agitatorMotor.set(vortexPower);
		*/
		
		if (oi.unJamShooter.get()) {
			fireEnabled = false;
			fireCount = 0;
			vortex.setVortexPower(1);
			receiver.setReceiverPower(-1);
			RobotMap.agitatorMotor.set(-1);
		}
		
		// Vortex and receiver
		if (OI.startFire.get()) {
			fireEnabled = true;
		}
		
		if (fireEnabled) {
			if (fireCount > 20 && fireCount <= 40) {
				// Run reverse
				vortex.setVortexPower(-1);
				receiver.setReceiverPower(1);
				RobotMap.agitatorMotor.set(-1);
			} else {
			// Run forward
			vortex.setVortexPower(-1);
			receiver.setReceiverPower(1);
			RobotMap.agitatorMotor.set(1);
			}
			fireCount++;
			if (fireCount % 40 == 0) {
				fireCount = 0;
			}
		}
		
		if (oi.stopFire.get()) {
			fireEnabled = false;
			fireCount = 0;
			vortex.setVortexPower(0);
			receiver.setReceiverPower(0);
			RobotMap.agitatorMotor.set(0);
		}
		
		// Control turret
		if (oi.turretPanLeft.get() || oi.turretPanRight.get()) {
			turret.controlTurretPower();
		}
		
		
		
		// Control basin
		if (oi.basinOut.get()) {
			if (gearPistonOut == true) {
				RobotMap.basinPiston.set(DoubleSolenoid.Value.kReverse); // Both pistons
				basinPistonOut = true;
			} else {
				RobotMap.gearPiston.set(DoubleSolenoid.Value.kReverse);
				RobotMap.basinPiston.set(DoubleSolenoid.Value.kReverse);
			}
		
		}
		
		if (oi.basinIn.get()) {
			RobotMap.basinPiston.set(DoubleSolenoid.Value.kForward); // Both pistons
			basinPistonOut = false;
		}
		
		// gear
		/*
		if (oi.gearOut.get()) {
			RobotMap.gearPiston.set(DoubleSolenoid.Value.kReverse); // Both pistons
			gearPistonOut = true;
		}
		
		if (oi.gearIn.get()) {
			RobotMap.gearPiston.set(DoubleSolenoid.Value.kForward); // Both pistons
			gearPistonOut = false;
			}
		*/
		// climber
		
		if (oi.climbPistonOut.get()) {
			RobotMap.climbPiston.set(DoubleSolenoid.Value.kReverse);
		}
		
		if (oi.climbPistonIn.get()) {
			RobotMap.climbPiston.set(DoubleSolenoid.Value.kForward);
		}
		
	
		
		
		/*if (Tegra.x != -1) {
			//if (gotToPosition) {
				gotToPosition = false;
			System.out.println("Turret at " + RobotMap.turretMotor.getPosition() + " trying to turn " + Tegra.theta + " to " + (RobotMap.turretMotor.getPosition() + Tegra.theta));
		turret.controlTurretPositionRelative(RobotMap.turretMotor.getPosition() + Tegra.theta);
		//	}
		}*/
		//turret.controlTurretPositionRelative(0.5);
		//turret.controlTurretPositionRelative(OI.joystickOne.getRawAxis(5) * 1);
		
		// Control climber
		
		if (oi.joystickOne.getPOV() == 90) {
			lifterPower += 0.05;
		}
		if (oi.joystickOne.getPOV() == 270 || oi.joystickOne.getPOV() == 315 || oi.joystickOne.getPOV() == 225){
			lifterPower = 0;
		}
		if (lifterPower > 1) {
			lifterPower = 1;
		}
		if (lifterPower < 0) {
			lifterPower = 0;
		}
		
		
		
		//System.out.println("lifterPower " + lifterPower);
		
		RobotMap.climbMotorOne.set(lifterPower);
		RobotMap.climbMotorTwo.set(lifterPower);
		/*
		 //COMP BOT
		if (OI.climbFullPower.get()) {
			RobotMap.climbMotorOne.set(1); //negative
			RobotMap.climbMotorTwo.set(1); //positive
		} else {
			RobotMap.climbMotorOne.set(0);
			RobotMap.climbMotorTwo.set(0);
		}*/
		
		
		//PRACTICE BOT
		/*
		if (OI.climbFullPower.get()) {
			RobotMap.climbMotorOne.set(1); //negative
			RobotMap.climbMotorTwo.set(1); //positive
		} else {
			RobotMap.climbMotorOne.set(0);
			RobotMap.climbMotorTwo.set(0);
		}
		*/
		//RobotMap.climbMotorOne.set(0); //negative
		//RobotMap.climbMotorTwo.set(0); //positive
	
		/*
		if (oi.joystickTwo.getPOV() == 90) {
			turretPower = (float) 0.2;
		} else if (oi.joystickTwo.getPOV() == 270 || oi.joystickOne.getPOV() == 315 || oi.joystickOne.getPOV() == 225){
			turretPower = (float )-0.2;
		} else {
			turretPower = 0;
		}
		
		RobotMap.turretMotor.set(turretPower);
		*/
		
		
		
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
		
		//System.out.println(RobotMap.navx.getYaw());
		//System.out.println("current: " + RobotMap.rightMotorOne.getOutputCurrent() + " voltage: " + RobotMap.rightMotorOne.getOutputVoltage() + " max " + tempMaxCurrent);
		
		/*
		if (RobotMap.rightMotorOne.getOutputCurrent() > tempMaxCurrent) {
			tempMaxCurrent = (float) RobotMap.rightMotorOne.getOutputCurrent();
			if (RobotMap.rightMotorTwo.getOutputCurrent() > tempMaxCurrent) {
				tempMaxCurrent = (float) RobotMap.rightMotorTwo.getOutputCurrent();
				if (RobotMap.leftMotorOne.getOutputCurrent() > tempMaxCurrent) {
					tempMaxCurrent = (float) RobotMap.leftMotorOne.getOutputCurrent();
					if (RobotMap.leftMotorTwo.getOutputCurrent() > tempMaxCurrent) {
						tempMaxCurrent = (float) RobotMap.leftMotorTwo.getOutputCurrent();
					}
				}
			}
		}
		*/
		//SmartDashboard.putNumber("rightMotorOne Current", RobotMap.rightMotorOne.getOutputCurrent());
		
		//SmartDashboard.putNumber("Turret position", RobotMap.turretMotor.getPosition());
		SmartDashboard.putNumber("NavX Yaw" , RobotMap.navx.getYaw());
		SmartDashboard.putNumber("Flywheel speed", RobotMap.flywheelMaster.getSpeed());
		
		//System.out.println("gear " + gearPistonOut + " basin " + basinPistonOut);
		//System.out.println("Flywheel position " + RobotMap.flywheel.getPosition());
		//System.out.println("Flywheel power " + RobotMap.flywheelMaster.getOutputVoltage());
	//	System.out.println("Tegra distance " + Tegra.distance);
		
		/*if (Math.abs(RobotMap.turretMotor.getSpeed()) > 55) {
    		System.out.println("Ended timer");
    		System.out.println("Timer: " + Timer.getFPGATimestamp());
    		System.out.println("Current acceleration: " + ((RobotMap.turretMotor.getSpeed() - previousVelocity) / (Timer.getFPGATimestamp() - previousTime)) );
    		System.out.println(startTime - Timer.getFPGATimestamp());
    	}
		
		previousVelocity = RobotMap.turretMotor.getSpeed();
    	previousTime = Timer.getFPGATimestamp();*/
		
		//System.out.println("Tegra Y " + Tegra.y);
		//System.out.println("Right encoder speed " + RobotMap.rightMotorOne.getSpeed());
		//System.out.println("Left encoder speed " + RobotMap.leftMotorOne.getSpeed());
		
		// Encoder sees 50 rotations, wheel turns 22 times
		
	//	System.out.println("Left trigger: " + OI.joystickOne.getRawAxis(2) + " Right trigger: " + OI.joystickOne.getRawAxis(3));
		
		System.out.println("Right Pos " + RobotMap.rightMotorOne.getPosition() + " Left Pos " + RobotMap.leftMotorOne.getPosition());
		//System.out.println("Right side no enc " + RobotMap.rightMotorTwo.getPosition() + " Left side no enc " + RobotMap.leftMotorTwo.getPosition());
		//System.out.println("Left Encoder Position " + RobotMap.leftMotorOne.getPosition());
		//System.out.println("gear intake current draw " + RobotMap.gearIntakeRotate.getOutputCurrent());
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
