package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware;

@Autonomous(name = "UnLatch")
public class Unlatch extends LinearOpMode {


    private Hardware hw;

    @Override
    public void runOpMode() throws InterruptedException {

        hw = new Hardware(hardwareMap);

//        Set all the encoders to 0
        hw.getlDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getcDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getrDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getlDrive().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hw.getcDrive().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hw.getrDrive().setMode(DcMotor.RunMode.RUN_TO_POSITION);


        telemetry.addLine("Waiting for start");
        telemetry.update();

        MotorController mc = new MotorController(hw);
        mc.start();
        mc.setLatchPos(0);

        Functions f = new Functions(hw);

        while(!opModeIsActive()){}

        waitForStart();

        mc.setLatchPos(1);

        sleep(2000);

        f.reverse(5);

        mc.setLatchPos(0);

        sleep(2000);

        mc.threadStop();

    }
}
