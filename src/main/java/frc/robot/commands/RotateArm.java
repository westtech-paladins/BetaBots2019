/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


/**
 * An example command.  You can replace me with your own command.
 */
public class RotateArm extends Command {
	private long   startTime;
	private double speed;
	
	public RotateArm(double speed) {
		this.speed = speed;
		requires(Robot.armMotor);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.armMotor.setMotorSpeed(speed);
	}
	
	@Override protected boolean isFinished() {
		return false;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.armMotor.setMotorSpeed(0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.armMotor.setMotorSpeed(0);
	}
}
