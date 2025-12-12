package frc.robot.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveArc;
import frc.robot.subsystems.Drivetrain;

public class BaseToCenterB extends SequentialCommandGroup {

    public BaseToCenterB(Drivetrain drivetrain) {

        addCommands(
                new DriveArc(1, 1, drivetrain, 1500)
        );
    }
}
