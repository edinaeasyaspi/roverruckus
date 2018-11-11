package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleOP.DriverAssistFunctions.Button;

import java.util.ArrayList;

@TeleOp(name = "ArmLatch", group = "Robot")
public class ArmLatch extends LinearOpMode {

    private ArrayList<String> data;
    private Hardware hw;
    private boolean hangerUp;
    private double hanger, hSpeed, tHanger, targetSpeed, position, hLastPos, refreshRate;
    private int encoder;
    private final double HANGER_MAX_SPEED = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        resetStartTime();

        hw = new Hardware(hardwareMap);
        Button a = new Button();
        data = new ArrayList<>();

//        Init all variables
        hanger = 0;
        tHanger = (double)(5/2750);
        hLastPos = 0;
        hangerUp = false;

//        Telemetry to fetch error if there is one
        telemetry.addLine(hw.getError());
        telemetry.addLine("Waiting...");
        telemetry.update();

        waitForStart();

        new Thread(){
            public void run(){

                while(getRuntime() < 10) {
                    encoder = hw.getHanger().getCurrentPosition();
                }

            }
        }.start();

        new Thread(){
            public void run(){

                while(getRuntime() < 10) {
                    data.add("Encoder value: " + encoder + ", Motor power: " + hanger + ", Target Speed: " + targetSpeed + "Measured speed: " + hSpeed);
                    ArmLatch.this.sleep(100);
                }

            }
        }.start();

        while(opModeIsActive()){

            position = (double)encoder / 2800;

//            Code to assign buttons
            if(a.get(gamepad1.a)){
                hangerUp = !hangerUp;
            }
//            Set target encoder positions
            tHanger = hangerUp? 1 : (double) 1/56;

//            Set power for motors
            targetSpeed = (tHanger - position);
            hSpeed = position - hLastPos;
            hLastPos = position;
            hanger = (targetSpeed + (targetSpeed - hSpeed)) * -1;
//            hanger = Math.round(hanger * 10000) * 0.0001;

//            Constrain motor power
            hanger = !(Math.abs(hanger) > HANGER_MAX_SPEED) ? hanger : HANGER_MAX_SPEED * (Math.abs(hanger) / hanger);

//            Telemetry
            telemetry.addData("Additional speed", ((targetSpeed - hSpeed)));
            telemetry.addData("Target position", tHanger);
            telemetry.addData("Actual power", hanger);
            telemetry.addData("Actual speed", hSpeed);
            telemetry.addData("Target Speed", targetSpeed);
            telemetry.addLine("Position Encoders: " + encoder + ", Position decimal: " + position);
            telemetry.addData("Update time", refreshRate);
            telemetry.addData("Time running", getRuntime());
            telemetry.update();

            if(true && Math.abs(tHanger - position) > 0.005){
                hw.getHanger().setPower(hanger);
            } else{
                hw.getHanger().setPower(0);
            }
//            hw.getHanger().setPower(gamepad1.left_stick_y);


//            if(encoder >= 1000){
//                telemetry.addData("Stopped position", encoder);
//                hw.getHanger().setPower(0);
//                break;
//            }
        }

        for(String s : data){
            telemetry.addLine(s);
        }

        telemetry.update();

        while(opModeIsActive()){
            sleep(1);
        }

    }
}
