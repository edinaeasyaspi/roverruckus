package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp(name = "MotorTester", group = "Robot")
public class MotorTester extends LinearOpMode {
    Hardware hw;


    @Override
    public void runOpMode() throws InterruptedException {
        hw = new Hardware(hardwareMap);

        telemetry.addLine(hw.getError());
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("rDrive", hw.getrDrive().getCurrentPosition());
            telemetry.addData("cDrive", hw.getcDrive().getCurrentPosition());
            telemetry.addData("lDrive", hw.getlDrive().getCurrentPosition());
            telemetry.addData("extender", hw.getExtender().getCurrentPosition());
            telemetry.addData("stanchion", hw.getStanchion().getCurrentPosition());
            telemetry.addData("hanger", hw.getHanger().getCurrentPosition());
            telemetry.update();

            hw.getStanchion().setPower(gamepad1.left_stick_y);
            hw.getExtender().setPower(gamepad1.right_stick_y);

        }

    }
}
