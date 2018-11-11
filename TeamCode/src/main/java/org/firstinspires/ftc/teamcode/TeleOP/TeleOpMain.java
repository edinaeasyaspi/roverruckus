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
    private double lDrive, cDrive, rDrive;
    private final double DRIVE_MAX_SPEED = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
//        Init the hardware
        hw = new Hardware(hardwareMap);
        Button a = new Button();

        MotorPositioner latch = new MotorPositioner(
        hw.getHanger(),
        40,
        3000,
        1,
        true
        );

        lDrive = 0;
        cDrive = 0;
        rDrive = 0;

        hangerUp = false;


        telemetry.addLine(hw.getError());
        telemetry.addLine("Waiting...");
        telemetry.update();

        waitForStart();

        telemetry.clearAll();

        while (opModeIsActive()) {

            lDrive = ((-1 * gamepad1.left_stick_y) + (gamepad1.right_stick_x * 0.5)) * DRIVE_MAX_SPEED;
            rDrive = ((-1 * gamepad1.left_stick_y) - (gamepad1.right_stick_x * 0.5)) * DRIVE_MAX_SPEED;
            cDrive = (gamepad1.right_trigger - gamepad1.left_trigger) * DRIVE_MAX_SPEED;

//            Constrain the drive speeds
            lDrive = !(Math.abs(lDrive) > DRIVE_MAX_SPEED) ? lDrive : DRIVE_MAX_SPEED * (Math.abs(lDrive) / lDrive);
            cDrive = !(Math.abs(cDrive) > DRIVE_MAX_SPEED) ? cDrive : DRIVE_MAX_SPEED * (Math.abs(cDrive) / cDrive);
            rDrive = !(Math.abs(rDrive) > DRIVE_MAX_SPEED) ? rDrive : DRIVE_MAX_SPEED * (Math.abs(rDrive) / rDrive);

//            Change hangerUp with button a
            if(a.get(gamepad1.a)){
                hangerUp = !hangerUp;
            }

//            Change target position based on hangerUp
            latch.setTargetPosition(hangerUp? 0.9 : 0.05);

//            Output stats to the driver
            telemetry.addData("lDrive", lDrive);
            telemetry.addData("rDrive", rDrive);
            telemetry.update();

//            Actually set the power from the hardware
            hw.getlDrive().setPower(lDrive);
            hw.getcDrive().setPower(cDrive);
            hw.getrDrive().setPower(rDrive);
            latch.move();

        }
    }

}
