/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;


/**
 * An example command.  You can replace me with your own command.
 */
public class DriveInput extends Command {
	
	public DriveInput() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrainSubsystem);
		requires(Robot.towerMotor);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.driveTrainSubsystem.arcadeDrive(OI.driverController.getY(), (Math.abs(OI.driverController.getX()) > Math.abs(OI.driverController.getZ())
																		   ? OI.driverController.getX()
																		   : OI.driverController.getZ()));
		Robot.towerMotor.usePOV(OI.driverController.getPOV());
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrainSubsystem.arcadeDrive(0, 0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.driveTrainSubsystem.arcadeDrive(0, 0);
	}
	
}
