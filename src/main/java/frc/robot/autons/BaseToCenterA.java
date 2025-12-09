package frc.robot.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.DriveTurn;
import frc.robot.subsystems.Drivetrain;

public class BaseToCenterA extends SequentialCommandGroup {

    public BaseToCenterA(Drivetrain drivetrain) {
        addCommands(
                new DriveDistance(0.5, 10, drivetrain),
                new DriveTurn(0.5, 90, 3, drivetrain)
        );
    }
}
//is movement made by stacking steps below each other?
