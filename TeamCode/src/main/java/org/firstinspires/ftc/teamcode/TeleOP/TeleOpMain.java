package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleOP.DriverAssistFunctions.Button;


@TeleOp(name = "TeleOpMain", group = "Robot")
public class TeleOpMain extends LinearOpMode {

    private Hardware hw;
    private double lDrive, cDrive, rDrive, hanger;
    private boolean hangerUp;
    private final double MAXSPEED = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
//        Init the hardware
        hw = new Hardware(hardwareMap);
        Button a = new Button();

        lDrive = 0;
        cDrive = 0;
        rDrive = 0;
        hanger = 0;
        hangerUp = false;

        telemetry.addLine(hw.getError());
        telemetry.addLine("Waiting...");
        telemetry.update();

        waitForStart();

        telemetry.clearAll();

        while (opModeIsActive()) {

            lDrive = ((-1 * gamepad1.left_stick_y) + (gamepad1.right_stick_x * 0.5)) * MAXSPEED;
            rDrive = ((-1 * gamepad1.left_stick_y) - (gamepad1.right_stick_x * 0.5)) * MAXSPEED;
            cDrive = (gamepad1.right_trigger - gamepad1.left_trigger) * MAXSPEED;
            hanger = (-1 * gamepad2.left_stick_y);

//            Constrain the drive speeds
            lDrive = !(Math.abs(lDrive) > MAXSPEED) ? lDrive : MAXSPEED * (Math.abs(lDrive) / lDrive);
            cDrive = !(Math.abs(cDrive) > MAXSPEED) ? cDrive : MAXSPEED * (Math.abs(cDrive) / cDrive);
            rDrive = !(Math.abs(rDrive) > MAXSPEED) ? rDrive : MAXSPEED * (Math.abs(rDrive) / rDrive);

//            Code to assign buttons
            if(a.get(gamepad1.a)){
                hangerUp = !hangerUp;
            }

//            Output stats to the driver
            telemetry.addData("Hanger Encoder Value", hw.getHanger().getCurrentPosition());
            telemetry.addData("Hanger Up?", hangerUp);
            telemetry.addData("lDrive", lDrive);
            telemetry.addData("rDrive", rDrive);
            telemetry.update();

//            Actually set the power from the hardware
            hw.getlDrive().setPower(lDrive);
            hw.getcDrive().setPower(cDrive);
            hw.getrDrive().setPower(rDrive);
            hw.getHanger().setPower(hanger);

        }
    }

}
