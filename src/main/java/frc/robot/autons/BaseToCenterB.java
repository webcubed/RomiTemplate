package frc.robot.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.DriveTurn;
import frc.robot.commands.TurnDegrees;

public class BaseToCenterB extends SequentialCommandGroup {

    public BaseToCenterB(Drivetrain drivetrain) {

        addCommands(
                new DriveDistance(0.5, 17.5, drivetrain),
                new DriveTurn(0.5, 90, 1, drivetrain),
                new DriveDistance(0.5, 10, drivetrain)
                        
        );
    }
}
