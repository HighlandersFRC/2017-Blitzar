package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4499.robot.commands.ExampleCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
	
	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	///////////////////////////////////////////////////////////////////////////////////
	
	
	
	// Pilot
	// Drive train, climber
	
	public static Joystick joystickOne = new Joystick(0);

	
	// Climber piston
	public static JoystickButton climbPistonOut = new JoystickButton(joystickOne, 6); // Right bumper
	public static JoystickButton climbPistonIn = new JoystickButton(joystickOne, 5); // Left bumper
	
	// Basin
	public static JoystickButton basinOut = new JoystickButton(joystickOne, 2); // B
	public static JoystickButton basinIn = new JoystickButton(joystickOne, 3); // X
	

	
	/////////
	
	
	// Co pilot
	// Turret control
	// Flywheel, Turret, Receiver, Vortex
	
	public static Joystick joystickTwo = new Joystick(1);
	
	//gear
	public static JoystickButton gearOut = new JoystickButton(joystickOne, 7); // 
	public static JoystickButton gearIn = new JoystickButton(joystickOne, 8); // 
	
	// Flywheel 
	public static JoystickButton flyWheelSpeedDecrease = new JoystickButton(joystickTwo, 1); // A
	public static JoystickButton flyWheelSpeedIncrease = new JoystickButton(joystickTwo, 4); // Y
	
	// Receiver
	public static JoystickButton receiverSpeedDecrease = new JoystickButton(joystickTwo, 7); // Back
	public static JoystickButton receiverSpeedIncrease = new JoystickButton(joystickTwo, 8); // Start
	
	// Turret
	public static JoystickButton turretPanRight = new JoystickButton(joystickTwo, 6); // Right bumper
	public static JoystickButton turretPanLeft = new JoystickButton(joystickTwo, 5); // Left bumper
	
	// Vortex
	public static JoystickButton vortexSpeedDecrease = new JoystickButton(joystickTwo, 3); // X
	public static JoystickButton vortexSpeedIncrease = new JoystickButton(joystickTwo, 2); // B
	
	// Vortex and Receiver
	public static JoystickButton startFire = new JoystickButton(joystickTwo, 8);
	public static JoystickButton stopFire = new JoystickButton(joystickTwo, 7);
	////////
	
	// Dial/switch box
	public static Joystick dial = new Joystick(2);
	public static JoystickButton dialOne = new JoystickButton(dial,1);
	public static JoystickButton dialTwo = new JoystickButton(dial,2);
	public static JoystickButton dialThree = new JoystickButton(dial,3);
	public static JoystickButton dialFour = new JoystickButton(dial,4);
	public static JoystickButton dialFive = new JoystickButton(dial,5);
	public static JoystickButton switchOne = new JoystickButton(dial, 6);
	public static JoystickButton switchTwo = new JoystickButton(dial, 7);
	public static JoystickButton switchThree = new JoystickButton(dial, 8);
	
	
}
