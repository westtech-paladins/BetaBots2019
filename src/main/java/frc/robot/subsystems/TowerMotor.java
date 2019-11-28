/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;


public class TowerMotor extends Subsystem {
	
	private PWM towerMotor;
	
	@Override
	public void initDefaultCommand() {
		this.towerMotor = new PWM(RobotMap.TOWER_MOTOR);
	}
	
	public void usePOV(int pov) {
		if (pov == -1) {
			setMotorSpeed(0);
		} else {
			if (pov >= 180) {
				setMotorSpeed(-1);
			} else {
				setMotorSpeed(1);
			}
		}
	}
	
	public void setMotorSpeed(double speed) {
		towerMotor.setSpeed(applyDeadband(limit(speed), 0.02));
	}
	
	private double applyDeadband(double value, double deadband) {
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
	
	private double limit(double value) {
		if (value > 1.0) {
			return 1.0;
		}
		return Math.max(value, -1.0);
	}
}