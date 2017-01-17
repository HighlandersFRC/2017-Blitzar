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
	
	public static Joystick joystickOne = new Joystick(0);
	
	public static JoystickButton flyWheelSpeedIncrease = new JoystickButton(joystickOne, 4); // Y
	public static JoystickButton flyWheelSpeedDecrease = new JoystickButton(joystickOne, 1); // A
	public static JoystickButton receiverSpeedIncrease = new JoystickButton(joystickOne, 2); // B
	public static JoystickButton receiverSpeedDecrease = new JoystickButton(joystickOne, 3); // X
	
	public static JoystickButton turretPanRight = new JoystickButton(joystickOne, 6); // Right bumper
	public static JoystickButton turretPanLeft = new JoystickButton(joystickOne, 5); // Left bumper
	
	public static JoystickButton vortexSpeedDecrease = new JoystickButton(joystickOne, 7); // Back
	public static JoystickButton vortexSpeedIncrease = new JoystickButton(joystickOne, 8); // Start
	
}
