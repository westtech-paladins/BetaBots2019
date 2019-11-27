package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class RotateArmTimed extends CommandGroup {
	
	public RotateArmTimed() {
		addSequential(new RotateArm(1), 1.1);
		addSequential(new RotateArm(-1), 1.1);
	}
}
