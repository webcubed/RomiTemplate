package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class ChangeInputSpeed extends InstantCommand {
    public ChangeInputSpeed(double factor) {
        super(() -> RobotContainer.reductionFactor = factor);
    }
}
