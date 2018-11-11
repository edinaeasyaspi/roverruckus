package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp(name = "ArmRaise", group = "Robot")
@Disabled
public class ArmRaise extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Hardware hw = new Hardware(hardwareMap);

        waitForStart();

        hw.getHanger().setPower(-0.01);

        while(opModeIsActive()){}
    }
}
