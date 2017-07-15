package org.usfirst.frc.team5203.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 Basic code for the two-speed gearbox in a tank-drive
 */
public class TwoSpeedRobot extends SampleRobot {
	//Instance naming
	RobotDrive myRobot;  // class that handles basic drive operations
	Joystick gamePad;
	Compressor compressor;
	DoubleSolenoid leftShifter;
	DoubleSolenoid rightShifter;
	Encoder encoder;
  
   //variable definitions
   static final int c_motorChannelLeft = 0;
   static final int c_motorChannelRight = 1;
   static final int c_GamepadChannel = 0;
   static final int c_DriveJoystickAxis = 1;
   boolean highGearTrue = false;
    
    public TwoSpeedRobot() 
    	{
    	//Channel assignments
        myRobot = new RobotDrive(c_motorChannelLeft, c_motorChannelRight);
        myRobot.setExpiration(0.1);
        gamePad = new Joystick(c_GamepadChannel);
        compressor = new Compressor();
        leftShifter = new DoubleSolenoid(1,2);
        rightShifter = new DoubleSolenoid(3,4);
        encoder = new Encoder(3,4);
        }
  
    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
       	//Startup variables
    	leftShifter.set(DoubleSolenoid.Value.kForward);
    	rightShifter.set(DoubleSolenoid.Value.kForward);
        compressor.start();
        highGearTrue = false;
        encoder.reset();
    	//End startup variables
        while (isOperatorControl() && isEnabled()) {
        	SmartDashboard.putNumber("encoder value", encoder.get());
        	//Control robot speed with joystick axes
        	myRobot.arcadeDrive(gamePad.getRawAxis(2),gamePad.getRawAxis(3), true);
        	
        	//Shift to high gear when pressing button
        	if (gamePad.getRawButton(1)==true)
        	{
        		highGearTrue = true;
               	leftShifter.set(DoubleSolenoid.Value.kReverse);
            	rightShifter.set(DoubleSolenoid.Value.kReverse);
        	}
        	//Shift to low gear when pressing button
            if (gamePad.getRawButton(2)==true)
            {
        		highGearTrue = false;
               	leftShifter.set(DoubleSolenoid.Value.kForward);
            	rightShifter.set(DoubleSolenoid.Value.kForward);
            }
        	
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

}
