// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DriveArc extends Command {

    private final Drivetrain m_drive;
    private final double leftSpeed;
    private final double rightSpeed;
    private final long time;
    private long startTime;

    /**
     * Creates a new DriveDistance. This command will drive your your robot for
     * a desired distance at a desired speed.
     *
     * @param speed The speed at which the robot will drive
     * @param inches The number of inches the robot will drive
     * @param drive The drivetrain subsystem on which this command will run
     */
    public DriveArc(double leftSpeed, double rightSpeed, Drivetrain drive, long time) {
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.time = time;
        startTime = 0;
        m_drive = drive;
        addRequirements(drive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        m_drive.tankDrive(leftSpeed, rightSpeed);
        m_drive.resetEncoders();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_drive.tankDrive(leftSpeed, rightSpeed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drive.tankDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        double t = System.currentTimeMillis();

        return Math.abs(t - startTime) >= time;
        // Compare distance travelled from start to desired distance
        // return Math.abs(m_drive.getAverageDistanceInch()) >= m_distance;
    }
}
