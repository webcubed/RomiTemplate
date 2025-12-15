// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.driveCommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class ArcDrive extends Command {

    public static boolean active = false;
    public static double speedFactor = 0.6; // Start slow, to be tested
    public static double turnFactor = 0.15;

    private final Drivetrain m_drivetrain;
    private final Supplier<Double> leftSpeedSupplier;
    private final Supplier<Double> rightSpeedSupplier;

    public ArcDrive(
            Drivetrain drivetrain,
            Supplier<Double> leftSpeedSupplier,
            Supplier<Double> rightSpeedSupplier) {
        m_drivetrain = drivetrain;
        this.leftSpeedSupplier = leftSpeedSupplier;
        this.rightSpeedSupplier = rightSpeedSupplier;
        addRequirements(drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        active = true;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // Axis 1: x values, forward/back
        // Axis 0: z values, left/right
        // Current behavior: W&A = forwards, S&D = backwards
        double axisX = leftSpeedSupplier.get();
        double axisZ = rightSpeedSupplier.get();
        // Looking at current code, W and S control forwards/backwards for left motor, Axis 1
        // Axis 0, A and D control right speed motor
        // So we need to map W and S (Axis 1) to BOTH left and right motor at base of 0.6
        // Then hitting axis 0, A and D, increases speed of either motor
        axisX = axisX * speedFactor;
        // Change this number MANUALLY, as this controls the maximum, and we don't always want the max to be 1
        axisZ = axisZ * turnFactor; // Only has 0.4 to move through (1 - 0.6), think of 0.4 as the whole, axis as the coefficient

        // Add inputs
        // Logic: turning right: X = 0.6, Z = 0.4 (assume full) -> leftspeed goes to 1, right speed goes to 0.2
        double leftSpeed = axisX + axisZ;
        // Turning left: X = 0.6, Z = -0.4 -> leftspeed = 0.2, right speed = 1 (This is very aggressive turning, consider no subtraction)

        double rightSpeed = axisX - axisZ;
        m_drivetrain.tankDrive(leftSpeed, rightSpeed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        active = false;
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
