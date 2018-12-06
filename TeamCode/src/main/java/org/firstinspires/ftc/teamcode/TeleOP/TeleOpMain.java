package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleOP.Tools.Button;
import org.firstinspires.ftc.teamcode.TeleOP.Tools.MotorPositioner;


@TeleOp(name = "TeleOpMain", group = "Robot")
public class TeleOpMain extends LinearOpMode {

        private Hardware hw;
        private boolean hangerUp;
        private double lDrive, cDrive, rDrive, sweeper, clutch;
        private final static double DRIVE_MAX_SPEED = 1;
        private double stanchionPos, extenderPos;
        private int stage;


        @Override
        public void runOpMode() throws InterruptedException {
//        Init the hardware
            hw = new Hardware(hardwareMap);
            Button a = new Button();

            MotorPositioner stanchion = new MotorPositioner(
                    hw.getStanchion(),
                    20,
                    6800,
                    0.5,
                    false
            );

//        This is how we will maintain the motor position for the latch
            MotorPositioner latch = new MotorPositioner(
                    hw.getHanger(), // The motor that we want to control
                    40, // This number should usually be somewhere in between 50 and 0
                    3000, // How far the motor actually goes in encoder counts
                    1, // The max speed that the motor is going to go
                    false // Whether the motor is reversed or not
            );
            MotorPositioner extender = new MotorPositioner(
                    hw.getExtender(),
                    40,
                    12000,
                    1,
                    false
            );

            lDrive = 0;
            cDrive = 0;
            rDrive = 0;
            Button change = new Button();

            stanchionPos = 0;
            extenderPos = 0;
            stage = 0;
            sweeper = 0.5;

            hangerUp = false;

            telemetry.addLine(hw.getError());
            telemetry.addLine("Waiting...");
            telemetry.update();

            waitForStart();

            telemetry.clearAll();

            while (opModeIsActive()) {

                lDrive = ((-1 * gamepad1.left_stick_y) + (gamepad1.right_stick_x * 0.5)) * DRIVE_MAX_SPEED;
                rDrive = ((-1 * gamepad1.left_stick_y) - (gamepad1.right_stick_x * 0.5)) * DRIVE_MAX_SPEED;
                cDrive = (gamepad1.left_stick_x) * DRIVE_MAX_SPEED * -2;

//            Constrain the drive speeds
                lDrive = !(Math.abs(lDrive) > DRIVE_MAX_SPEED) ? lDrive : DRIVE_MAX_SPEED * (Math.abs(lDrive) / lDrive);
                cDrive = !(Math.abs(cDrive) > DRIVE_MAX_SPEED * 2) ? cDrive : DRIVE_MAX_SPEED * 2 * (Math.abs(cDrive) / cDrive);
                rDrive = !(Math.abs(rDrive) > DRIVE_MAX_SPEED) ? rDrive : DRIVE_MAX_SPEED * (Math.abs(rDrive) / rDrive);

//                Give the clutch value
                clutch = 0.125 + (Math.abs(lDrive) + Math.abs(rDrive)) * 0.0625 - Math.abs(cDrive) * 0.125;

//            Change hangerUp with button a
                if (a.get(gamepad1.a)) {
                    hangerUp = !hangerUp;
                }

//            Change target position based on hangerUp
                latch.setTargetPosition(hangerUp ? 0.9 : clutch);

                if(hangerUp){
                    stage = 0;
                }

//            Setting the continuous servo power
                sweeper = 0.5 + (gamepad1.right_bumper ? 0.5 : 0) + (gamepad1.left_bumper ? -0.5 : 0);

//            Output stats to the driver
                telemetry.addData("lDrive", lDrive);
                telemetry.addData("rDrive", rDrive);
                telemetry.addData("Runtime", Math.round(getRuntime()));
                telemetry.update();

//                Mapping presets for sweeper arm
                if (gamepad1.x) {
                    stage = 1;
                }

                if (gamepad1.y) {
                    stage = 2;
                }

                if (gamepad1.b) {
                    stage = 3;
                }

//                Set motor positions based on presets
                if (change.get(gamepad1.x || gamepad1.y || gamepad1.b || hangerUp)) {
                    switch (stage) {
                        case 0:
                            stanchionPos = 0.25;
                            extenderPos = 0;
                            break;

                        case 1:
                            stanchionPos = 0;
                            extenderPos = 0.75;
                            break;

                        case 2:
                            stanchionPos = 0.5;
                            extenderPos = 0.5;
                            break;

                        case 3:
                            stanchionPos = 1;
                            extenderPos = 0.65;
                            break;

                    }
                }

//                Give the driver abilty to fine tune arm
                    extenderPos += (gamepad1.dpad_up ? 0.005 : 0) + (gamepad1.dpad_down ? -0.005 : 0);
                    stanchionPos += (gamepad1.dpad_left? 1 : 0 - (gamepad1.dpad_right? 1 : 0)) * 0.005;
//                    Constrain the value so there isn't any pressure
                    extenderPos = (extenderPos < 0 ? 0 : extenderPos > 1 ? 1 : extenderPos);
                    stanchionPos = (stanchionPos < 0 ? 0 : stanchionPos > 1 ? 1 : stanchionPos);

                extender.setTargetPosition(extenderPos);
                stanchion.setTargetPosition(stanchionPos);

//            Actually set the power from the hardware

                hw.getlDrive().setPower(lDrive);
                hw.getcDrive().setPower(cDrive);
                hw.getrDrive().setPower(rDrive);

                hw.getSweeper().setPosition(sweeper);

                latch.move();
                extender.move();
                stanchion.move();

            }
        }

    }
