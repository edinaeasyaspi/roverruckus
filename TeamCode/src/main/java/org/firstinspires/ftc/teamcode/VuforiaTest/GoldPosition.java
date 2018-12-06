package org.firstinspires.ftc.teamcode.VuforiaTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.Vuforia;

@TeleOp(name = "GoldPosition")

public class GoldPosition extends LinearOpMode {
    Hardware hw;
    Vuforia vf;

    @Override
    public void runOpMode() throws InterruptedException {
        hw = new Hardware(hardwareMap);
        vf = new Vuforia(hardwareMap);

        telemetry.addLine("Waiting");
        telemetry.addLine("Error: " + hw.getError());
        telemetry.update();

        waitForStart();

        vf.activate();

        while (opModeIsActive()) {
            double goldX = vf.getGoldX();

            telemetry.addLine((goldX == Double.NaN) ? "No gold detected" : ("Gold position: " + goldX));
            telemetry.addLine("This is a test");

            if (vf.getObjects() != null) {
                for (Recognition object : vf.getObjects()) {
                    telemetry.addLine(object.getLabel());
                }
            }

            telemetry.update();

        }

        vf.deactivate();

    }
}
