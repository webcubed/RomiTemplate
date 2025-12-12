package frc.robot.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveArc;
import frc.robot.subsystems.Drivetrain;

// EXAMPLE AUTON (used for testing)
public class ExampleAuton extends SequentialCommandGroup {

    public ExampleAuton(Drivetrain drivetrain) {
        addCommands(
                new DriveArc(1, 1, drivetrain, 1000)
        );
    }

}
