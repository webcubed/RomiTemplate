// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.romi.OnBoardIO;
import edu.wpi.first.wpilibj.romi.OnBoardIO.ChannelMode;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.autons.BaseToCenterA;
import frc.robot.autons.BaseToCenterB;
import frc.robot.autons.ExampleAuton;
import frc.robot.commands.ChangeInputSpeed;
import frc.robot.commands.DriveArc;
import frc.robot.driveCommands.ArcDrive;
import frc.robot.driveCommands.ArcadeDrive;
import frc.robot.driveCommands.TankDrive;
import frc.robot.subsystems.Drivetrain;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer implements NativeKeyListener {

    // The robot's subsystems and commands are defined here...
    public static double reductionFactor = 2;
    private final Drivetrain m_drivetrain = new Drivetrain();
    private final OnBoardIO m_onboardIO = new OnBoardIO(ChannelMode.INPUT, ChannelMode.INPUT);

    // Assumes a gamepad plugged into channel 0
    private final Joystick m_controller = new Joystick(0);
    private final JoystickButton keyZ = new JoystickButton(m_controller, 90);
    private final JoystickButton keyX = new JoystickButton(m_controller, 88);
    private final JoystickButton keyC = new JoystickButton(m_controller, 67);
    private final JoystickButton keyV = new JoystickButton(m_controller, 86);
    private final JoystickButton keyI = new JoystickButton(m_controller, 73);
    private final JoystickButton keyJ = new JoystickButton(m_controller, 74);
    private final JoystickButton keyK = new JoystickButton(m_controller, 75);
    private final JoystickButton keyL = new JoystickButton(m_controller, 76);
    private final JoystickButton keyPageUp = new JoystickButton(m_controller, 266);
    private final JoystickButton keyPageDown = new JoystickButton(m_controller, 267);

    // Create SmartDashboard chooser for autonomous routines
    private final SendableChooser<Command> m_chooser = new SendableChooser<>();

    // NOTE: The I/O pin functionality of the 5 exposed I/O pins depends on the hardware "overlay"
    // that is specified when launching the wpilib-ws server on the Romi raspberry pi.
    // By default, the following are available (listed in order from inside of the board to outside):
    // - DIO 8 (mapped to Arduino pin 11, closest to the inside of the board)
    // - Analog In 0 (mapped to Analog Channel 6 / Arduino Pin 4)
    // - Analog In 1 (mapped to Analog Channel 2 / Arduino Pin 20)
    // - PWM 2 (mapped to Arduino Pin 21)
    // - PWM 3 (mapped to Arduino Pin 22)
    //
    // Your subsystem configuration should take the overlays into account
    /**
     * The container for the robot. Contains subsystems, OI devices, and
     * commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        // Register global keyboard hook only when running in simulation
        try {
            if (!edu.wpi.first.wpilibj.RobotBase.isReal()) {
                GlobalScreen.registerNativeHook();
                GlobalScreen.addNativeKeyListener(this);
            }
        } catch (NativeHookException e) {
            System.err.println("Failed to register global keyboard hook: " + e.getMessage());
        }
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // Default command is arcade drive. This will run unless another command
        // is scheduled over it.
        // m_drivetrain.setDefaultCommand(getArcadeDriveCommand());
        // Speed & rotation
        //m_drivetrain.setDefaultCommand(getTankDriveCommand());
        // left & right motor speed
        m_drivetrain.setDefaultCommand(getArcDriveCommand());
        // combination of both
        // Example of how to use the onboard IO
        // Later
        Trigger onboardButtonA = new Trigger(m_onboardIO::getButtonAPressed);
        onboardButtonA
                .onTrue(new PrintCommand("Button A Pressed"))
                .onFalse(new PrintCommand("Button A Released"));
        // J and L for 90 deg increments
        keyJ.onTrue(new DriveArc(0.5, 1, m_drivetrain, 500));
        keyL.onTrue(new DriveArc(1, 0.5, m_drivetrain, 500));

        // Z for slow, X for fast (man)
        keyZ.onTrue(new ChangeInputSpeed(1.5));
        keyX.onTrue(new ChangeInputSpeed(1));
        // Setup SmartDashboard options
        m_chooser.setDefaultOption("Example Auton", new ExampleAuton(m_drivetrain));
        // A has a rectangle
        m_chooser.addOption("BaseToCenterA", new BaseToCenterA(m_drivetrain));
        // B has the triangle
        m_chooser.addOption("BaseToCenterB", new BaseToCenterB(m_drivetrain));

        SmartDashboard.putData(m_chooser);

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return m_chooser.getSelected();
    }

    /**
     * Use this to pass the teleop command to the main {@link Robot} class.
     *
     * @return the command to run in teleop
     */
    public Command getArcadeDriveCommand() {
        return new ArcadeDrive(
                m_drivetrain, () -> -m_controller.getRawAxis(1) / reductionFactor, () -> -m_controller.getRawAxis(0) / reductionFactor);
    }

    public Command getTankDriveCommand() {
        return new TankDrive(
                m_drivetrain, () -> -m_controller.getRawAxis(1) / reductionFactor, () -> -m_controller.getRawAxis(0) / reductionFactor);
    }

    public Command getArcDriveCommand() {
        // No reduction factor because
        // 1. It's inherently slow
        // 2. Can cycle between drives systems
        return new ArcDrive(
                m_drivetrain, () -> -m_controller.getRawAxis(1), () -> -m_controller.getRawAxis(0));
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (!DriverStation.isTeleopEnabled()) {
            return;
        }

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case 33: // VK_PAGE_UP
                ArcDrive.speedFactor = Math.min(1.0, ArcDrive.speedFactor + 0.05);
                System.out.println("ArcDrive.speedFactor: " + String.format("%.2f", ArcDrive.speedFactor));
                break;
            case 34: // VK_PAGE_DOWN
                ArcDrive.speedFactor = Math.max(0.1, ArcDrive.speedFactor - 0.05);
                System.out.println("ArcDrive.speedFactor: " + String.format("%.2f", ArcDrive.speedFactor));
                break;
            case 67: // VK_C
                ArcDrive.turnFactor = Math.min(1.0, ArcDrive.turnFactor + 0.05);
                System.out.println("ArcDrive.turnFactor: " + String.format("%.2f", ArcDrive.turnFactor));
                break;
            case 86: // VK_V
                ArcDrive.turnFactor = Math.max(0.1, ArcDrive.turnFactor - 0.05);
                System.out.println("ArcDrive.turnFactor: " + String.format("%.2f", ArcDrive.turnFactor));
                break;
            case 90: // VK_Z
                System.out.println("Z pressed: input speed multiplier increased");
                break;
            case 88: // VK_X
                System.out.println("X pressed: input speed multiplier decreased");
                break;
            default:
                break;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}
