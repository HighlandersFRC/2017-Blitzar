package org.usfirst.frc.team4499.robot;
import org.usfirst.frc.team4499.robot.tools.DCMotor;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SerialPort;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checki--ng
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	
	public static DCMotor leftMotorOne = new DCMotor(1); //1 // Has encoder       accretion 2431
	public static DCMotor leftMotorTwo = new DCMotor(2); //2
	public static DCMotor rightMotorOne = new DCMotor(3); //3 // has the encoder
	public static DCMotor rightMotorTwo = new DCMotor(4); //4 // should have encoder
	
	// Accretion
	// yellow 2 encoder left
	// red 4 left no encoder
	// blue 3 no encoder right
	// green encoder right 1
	
	
	public static CANTalon flywheel = new CANTalon(5);
	public static CANTalon receiverLeft = new CANTalon(6);
	public static CANTalon receiverRight = new CANTalon(7);
	public static CANTalon turretMotor = new CANTalon(8);
	public static CANTalon vortexMotorOne = new CANTalon(9);
	public static CANTalon vortexMotorTwo = new CANTalon(10);
	
	public static AHRS navx = new AHRS(SerialPort.Port.kMXP);
	
	//Compressor compressor = new Compressor(0);
	public static DoubleSolenoid test = new DoubleSolenoid(0, 0, 1);
	
	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
