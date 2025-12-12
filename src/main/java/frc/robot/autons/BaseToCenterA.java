package frc.robot.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.DriveTurn;
import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.DriveArc;

public class BaseToCenterA extends SequentialCommandGroup {

    public BaseToCenterA(Drivetrain drivetrain) {
        addCommands(
                new DriveDistance(0.5, 8, drivetrain),
                new DriveArc(1, 0.5, drivetrain, 515),
                new DriveDistance(0.5, 7, drivetrain),
                new DriveArc(0.5, 1, drivetrain, 515),
                new DriveDistance(0.5, 14, drivetrain),
                new DriveArc(1, 0.5, drivetrain, 515),
                new DriveDistance(0.5, 18, drivetrain),
                new DriveArc(1, 0.5, drivetrain, 515),
                new DriveDistance(0.5, 10, drivetrain),
                new DriveArc(0.5, 1, drivetrain, 515),
                new DriveArc(2, 1, drivetrain, 300)
        );
    }
}
//is movement made by stacking steps below each other?
