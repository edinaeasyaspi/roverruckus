package org.firstinspires.ftc.teamcode.Autonomus.Comp1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Autonomus.Functions;
import org.firstinspires.ftc.teamcode.Autonomus.MotorController;
import org.firstinspires.ftc.teamcode.Hardware;

@Autonomous(group = "Robot", name = "Gold")
@Disabled
public class Gold extends LinearOpMode {

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

        waitForStart();

        mc.setLatchPos(1);

        sleep(1500);

        f.reverse(8.5);

        mc.setLatchPos(0.25);

        sleep(1000);

        f.forward(4);

        mc.setLatchPos(0);

        sleep(500);

        f.strafeLeft(15);

        mc.setLatchPos(0.25);

        sleep(500);

        f.reverse(20);

        f.right(135);

        mc.setLatchPos(0);

        sleep(500);

        f.strafeRight(18);

        mc.setLatchPos(0.25);

        sleep(500);

        f.reverse(34);

        hw.getSweeper().setPosition(0);

        sleep(2000);

        hw.getSweeper().setPosition(0.5);

        f.forward(75);

        mc.setLatchPos(0);

        sleep(1000);

        mc.threadStop();

    }
}
