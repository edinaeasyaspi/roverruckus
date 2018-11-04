package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware;

public class DriveByInches extends LinearOpMode {

    private Hardware hw;

    @Override
    public void runOpMode() throws InterruptedException {

        hw = new Hardware(hardwareMap);

//        Set all the encoders to 0
        hw.getlDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getcDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getrDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FileReader fr = new FileReader("DriveByInchesRun.txt");

        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();

        Functions f;

        for (String currentLine : fr.getLines()) {

            f = new Functions(hw);

            f.interpretLine(currentLine);

        }


    }
}
