package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DriveTurn extends Command {

    private final Drivetrain drivetrain;
    private final double speed, deg, inches;
    private double targetHeading;

    public DriveTurn(double speed, double deg, double inches, Drivetrain drivetrain) {
        this.speed = speed;
        this.deg = deg;
        this.drivetrain = drivetrain;
        this.inches = inches;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        targetHeading = drivetrain.getGyroAngleY() + deg;
        drivetrain.resetEncoders();
    }

    @Override
    public void execute() {
        double currentHeading = drivetrain.getGyroAngleY();
        double headingError = targetHeading - currentHeading;
        double turnSpeed = headingError * 0.01; // Proportional control for turning
        drivetrain.arcadeDrive(speed, turnSpeed);
    }

    @Override
    public boolean isFinished() {
        return drivetrain.getAverageDistanceInch() >= inches;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }
}
