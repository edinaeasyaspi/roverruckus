package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Drive", group = "Robot")

public class DriveTrain extends LinearOpMode {

    private Hardware hw;
    private double lDrive, cDrive, rDrive;
    private final double MAXSPEED = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
//        Init the hardware
        hw = new Hardware(hardwareMap);

        lDrive = 0;
        cDrive = 0;
        rDrive = 0;

        telemetry.addLine(hw.getError());
        telemetry.addLine("Waiting...");
        telemetry.update();

        waitForStart();

        telemetry.clearAll();

        while (opModeIsActive()) {

            lDrive = (-1 * gamepad1.left_stick_y + gamepad1.right_stick_x * 0.5) * MAXSPEED;
            rDrive = (-1 * gamepad1.left_stick_y - gamepad1.right_stick_x * 0.5) * MAXSPEED;
            cDrive = (gamepad1.right_trigger - gamepad1.left_trigger) * MAXSPEED;

//            Constrain the motor speeds
            lDrive = !(Math.abs(lDrive) > MAXSPEED) ? lDrive : MAXSPEED * Math.abs(lDrive) / lDrive;
            cDrive = !(Math.abs(cDrive) > MAXSPEED) ? cDrive : MAXSPEED * Math.abs(cDrive) / cDrive;
            rDrive = !(Math.abs(rDrive) > MAXSPEED) ? rDrive : MAXSPEED * Math.abs(rDrive) / rDrive;

//            Output stats to the driver
            telemetry.addData("Center encoder value", hw.getcDrive().getCurrentPosition());
            telemetry.update();

//            Actually set the power from the hardware
            hw.getlDrive().setPower(lDrive);
            hw.getcDrive().setPower(cDrive);
            hw.getrDrive().setPower(rDrive);

        }
    }

}
