package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleOP.DriverAssistFunctions.Button;

@TeleOp(name = "ArmLatch", group = "Robot")
public class ArmLatch extends LinearOpMode {

    private Hardware hw;
    private boolean hangerUp;
    private double hanger, hSpeed;
    private int tHanger, hLastPos;
    private final double HANGER_MAX_SPEED = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        hw = new Hardware(hardwareMap);
        Button a = new Button();

//        Init all variables
        hanger = 0;
        tHanger = 0;
        hLastPos = 0;
        hangerUp = false;

//        Telemetry to fetch error if there is one
        telemetry.addLine(hw.getError());
        telemetry.addLine("Waiting...");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){

//            Code to assign buttons
            if(a.get(gamepad1.a)){
                hangerUp = !hangerUp;
            }
//            Set target encoder positions
            tHanger = hangerUp? 2800 : 50;

//            Set power for motors
            hSpeed = Math.abs(hw.getHanger().getCurrentPosition() - hLastPos);
            hLastPos = hw.getHanger().getCurrentPosition();
            hanger = ((tHanger - hw.getHanger().getCurrentPosition()) + (Math.abs((Math.abs(hw.getHanger().getCurrentPosition() - hLastPos)) - hSpeed)) * Math.abs(tHanger - hw.getHanger().getCurrentPosition())/tHanger - hw.getHanger().getCurrentPosition()) * -0.000001;
            hanger = Math.round(hanger * 10000) * 0.0001;

//            Constrain motor power
            hanger = !(Math.abs(hanger) > HANGER_MAX_SPEED) ? hanger : HANGER_MAX_SPEED * (Math.abs(hanger) / hanger);

//            Actually set power to the motors in the hardware
            hw.getHanger().setPower(hanger);
        }

    }
}
