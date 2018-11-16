package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp(name = "EncoderTester", group = "Robot")
public class EncoderTester extends LinearOpMode {

    Hardware hw;

    @Override
    public void runOpMode() throws InterruptedException {

        hw = new Hardware(hardwareMap);

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a){
                resetEncoders();
            }

            telemetry.addData("rDrive", hw.getrDrive().getCurrentPosition());
            telemetry.addData("cDrive", hw.getcDrive().getCurrentPosition());
            telemetry.addData("lDrive", hw.getlDrive().getCurrentPosition());
            telemetry.update();

        }

    }

    private void resetEncoders(){

        hw.getlDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getcDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getrDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getlDrive().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hw.getcDrive().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hw.getrDrive().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
}
