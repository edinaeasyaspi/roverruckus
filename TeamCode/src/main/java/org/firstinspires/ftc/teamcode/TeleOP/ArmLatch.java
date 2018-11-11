package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleOP.Tools.Button;

import java.util.ArrayList;

@TeleOp(name = "ArmLatch", group = "Robot")
public class ArmLatch extends LinearOpMode {

    private ArrayList<String> data;
    private Hardware hw;
    private boolean hangerUp;
    private double hanger, hSpeed, tHanger, targetSpeed, position, hLastPos, addSpeed;
    private int encoder;
    private final double HANGER_MAX_SPEED = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        hw = new Hardware(hardwareMap);
        Button a = new Button();
        data = new ArrayList<>();

//        Init all variables
        hanger = 0;
        tHanger = (double) (5 / 2750);
        hLastPos = 0;
        hangerUp = false;

//        Telemetry to fetch error if there is one
        telemetry.addLine(hw.getError());
        telemetry.addLine("Waiting...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            encoder = hw.getHanger().getCurrentPosition();

            position = (double) encoder / 3000;

//            Code to assign buttons
            if (a.get(gamepad1.a)) {
                hangerUp = !hangerUp;
            }
//            Set target encoder positions
            tHanger = hangerUp ? 0.9 : (double)1/60;

//            Set power for motors
            targetSpeed = (tHanger - position);
            hSpeed = position - hLastPos;
            hLastPos = position;
            addSpeed = (targetSpeed - hSpeed) * 40;
            hanger = (targetSpeed + addSpeed) * -1;

//            Constrain motor power
            hanger = !(Math.abs(hanger) > HANGER_MAX_SPEED) ? hanger : HANGER_MAX_SPEED * (Math.abs(hanger) / hanger);

//            Telemetry
            telemetry.addData("Additional speed", addSpeed);
            telemetry.addData("Target position", tHanger);
            telemetry.addData("Actual power", hanger);
            telemetry.addData("Actual speed", hSpeed);
            telemetry.addData("Target Speed", targetSpeed);
            telemetry.addLine("Position Encoders: " + encoder + ", Position decimal: " + position);
            telemetry.update();

            hw.getHanger().setPower(hanger);

        }
    }

}
