/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.RobotMap;


@SuppressWarnings("DanglingJavadoc") public class DriveTrain extends Subsystem {
	/** Comment these out if you want to use PWM. Leave them here if you want to use CANbus. */
	VictorSPX leftMotor;
	/** Comment these out if you want to use PWM. Leave them here if you want to use CANbus. */
	VictorSPX rightMotor;
	
	VictorSPX armMotorI;
	VictorSPX armMotorII;
	
	
	/** Uncomment these if you want to use PWM Leave them commented out if you want to use CANbus */
	//PWMVictorSPX  leftMotor;
	/** Uncomment these if you want to use PWM Leave them commented out if you want to use CANbus */
	//PWMVictorSPX rightMotor = new VictorSPX(RobotMap.RIGHT_MOTOR);
	
	/**
	 * Constructor for the drive train. Initializes all the motors.
	 */
	public DriveTrain() {
		this.leftMotor = new VictorSPX(RobotMap.LEFT_MOTOR);
		this.rightMotor = new VictorSPX(RobotMap.RIGHT_MOTOR);
		this.armMotorI = new VictorSPX(RobotMap.ARM_MOTOR_I);
		this.armMotorII = new VictorSPX(RobotMap.ARM_MOTOR_II);
		/*
		this.leftMotor = new PWMVictorSPX(RobotMap.LEFT_MOTOR);
		this.leftMotor = new PWMVictorSPX(RobotMap.RIGHT_MOTOR);
		 */
	}
	
	@Override
	public void initDefaultCommand() { }
	
	/**
	 * @param moveSpeed
	 * 		Speed at which you want the robot to move. [-1.0..1.0]. Forward is positive.
	 * @param rotateAngle
	 * 		The angle at which you want the robot to move. [-1.0..1.0]. Clockwise is positive.
	 */
	public void arcadeDrive(double moveSpeed, double rotateAngle) {
		
		moveSpeed = applyDeadband(limit(moveSpeed), 0.02);
		rotateAngle = applyDeadband(limit(rotateAngle), 0.02);
		
		// Square the inputs (while preserving the sign) to increase fine control
		// while permitting full power.
		moveSpeed = Math.copySign(moveSpeed * moveSpeed, moveSpeed);
		rotateAngle = Math.copySign(rotateAngle * rotateAngle, rotateAngle);
		
		double leftMotorOutput;
		double rightMotorOutput;
		
		double maxInput = Math.copySign(Math.max(Math.abs(moveSpeed), Math.abs(rotateAngle)), moveSpeed);
		
		if (moveSpeed >= 0.0) {
			// First quadrant, else second quadrant
			if (rotateAngle >= 0.0) {
				leftMotorOutput = maxInput;
				rightMotorOutput = moveSpeed - rotateAngle;
			} else {
				leftMotorOutput = moveSpeed + rotateAngle;
				rightMotorOutput = maxInput;
			}
		} else {
			// Third quadrant, else fourth quadrant
			if (rotateAngle >= 0.0) {
				leftMotorOutput = moveSpeed + rotateAngle;
				rightMotorOutput = maxInput;
			} else {
				leftMotorOutput = maxInput;
				rightMotorOutput = moveSpeed - rotateAngle;
			}
		}
		
		leftMotor.set(ControlMode.PercentOutput, limit(leftMotorOutput));
		rightMotor.set(ControlMode.PercentOutput, limit(rightMotorOutput) * -1.0);
	}
	
	/**
	 * Returns 0.0 if the given value is within the specified range around zero. The remaining range between the deadband and 1.0 is scaled from 0.0 to 1.0.
	 *
	 * @param value
	 * 		value to clip
	 * @param deadband
	 * 		range around zero
	 */
	protected double applyDeadband(double value, double deadband) {
		if (Math.abs(value) > deadband) {
			if (value > 0.0) {
				return (value - deadband) / (1.0 - deadband);
			} else {
				return (value + deadband) / (1.0 - deadband);
			}
		} else {
			return 0.0;
		}
	}
	
	protected double limit(double value) {
		if (value > 1.0) {
			return 1.0;
		}
		return Math.max(value, -1.0);
	}
	
	public void tankDrive(double leftMoveSpeed, double rightMoveSpeed) {
		leftMoveSpeed = applyDeadband(limit(leftMoveSpeed), 0.02);
		rightMoveSpeed = applyDeadband(limit(rightMoveSpeed), 0.02);
		
		leftMoveSpeed = Math.copySign(leftMoveSpeed * leftMoveSpeed, leftMoveSpeed);
		rightMoveSpeed = Math.copySign(rightMoveSpeed * rightMoveSpeed, rightMoveSpeed);
		
		leftMotor.set(ControlMode.PercentOutput, limit(leftMoveSpeed));
		rightMotor.set(ControlMode.PercentOutput, limit(rightMoveSpeed) * -1.0);
		
	}
}