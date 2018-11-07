package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;


@TeleOp(name = "SweeperTest", group = "Robot")

public class SweeperTest extends LinearOpMode {
    private Hardware hw;

    @Override
    public void runOpMode() throws InterruptedException {

        hw = new Hardware(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                hw.getSweeper().setPosition(1);
            } else if (gamepad1.b) {
                hw.getSweeper().setPosition(0);
            } else {
                hw.getSweeper().setPosition(0.5);
            }
        }

    }
}
