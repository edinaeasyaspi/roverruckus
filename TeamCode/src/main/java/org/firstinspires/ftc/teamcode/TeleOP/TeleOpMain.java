package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleOP.DriverAssistFunctions.Button;


@TeleOp(name = "TeleOpMain", group = "Robot")
public class TeleOpMain extends LinearOpMode {

    private Hardware hw;
    private double lDrive, cDrive, rDrive, hanger, hSpeed;
    private boolean hangerUp;
    private int tHanger, hLastPos;
    private final double DRIVE_MAX_SPEED = 0.5;
    private final double HANGER_MAX_SPEED = 1;

    @Override
    public void runOpMode() throws InterruptedException {
//        Init the hardware
        hw = new Hardware(hardwareMap);
        Button a = new Button();

        lDrive = 0;
        cDrive = 0;
        rDrive = 0;
        hanger = 0;


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




//            Set power for motors
            hanger = gamepad2.left_stick_y;
            hanger = Math.round(hanger * 10000) * 0.0001;
//            hanger = 0.05;

//            Constrain motor power
            hanger = !(Math.abs(hanger) > HANGER_MAX_SPEED) ? hanger : HANGER_MAX_SPEED * (Math.abs(hanger) / hanger);


//            Output stats to the driver
            telemetry.addData("Hanger Encoder Value", hw.getHanger().getCurrentPosition());
            telemetry.addLine("Hanger power: " + hanger + ", Target Position: " + tHanger);
            telemetry.addData("Hanger Up?", hangerUp);
            telemetry.addData("lDrive", lDrive);
            telemetry.addData("rDrive", rDrive);
            telemetry.update();

//            Actually set the power from the hardware
            hw.getlDrive().setPower(lDrive);
            hw.getcDrive().setPower(cDrive);
            hw.getrDrive().setPower(rDrive);
            hw.getHanger().setPower(hanger);


        }
    }

}
