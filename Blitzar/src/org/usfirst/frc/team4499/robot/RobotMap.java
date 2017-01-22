package org.usfirst.frc.team4499.robot;
import com.ctre.CANTalon;
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
	
	public static CANTalon leftMotorOne = new CANTalon(1);
	public static CANTalon leftMotorTwo = new CANTalon(2);
	public static CANTalon rightMotorOne = new CANTalon(3);
	public static CANTalon rightMotorTwo = new CANTalon(4);
	public static CANTalon flywheel = new CANTalon(5);
	public static CANTalon receiverLeft = new CANTalon(6);
	public static CANTalon receiverRight = new CANTalon(7);
	public static CANTalon turretMotor = new CANTalon(8);
	public static CANTalon vortexMotorOne = new CANTalon(9);
	public static CANTalon vortexMotorTwo = new CANTalon(10);
	
	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
