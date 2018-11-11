package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleOP.Tools.Button;
import org.firstinspires.ftc.teamcode.TeleOP.Tools.MotorPositioner;

@TeleOp(name = "SweeperArm", group = "Robot")
public class SweeperArm extends LinearOpMode {
    private Hardware hw;
    private double stanchionPos, extenderPos, sweeper;
    private boolean extenstionAccess;
    private int stage;


    @Override
    public void runOpMode() throws InterruptedException {

        hw = new Hardware(hardwareMap);

        MotorPositioner stanchion = new MotorPositioner(
                hw.getStanchion(),
                20,
                6300,
                0.5,
                false
        );

        MotorPositioner extender = new MotorPositioner(
                hw.getExtender(),
                40,
                6000,
                1,
                false
        );

        Button change = new Button();

        stanchionPos = 0;
        extenderPos = 0;
        stage = 1;
        sweeper = 0.5;
        extenstionAccess = true;

        telemetry.addLine(hw.getError());
        telemetry.addLine("Waiting...");
        telemetry.update();

        waitForStart();

        telemetry.clearAll();

        while (opModeIsActive()) {

            telemetry.addData("Runtime", Math.round(getRuntime()));
            telemetry.update();

            if(gamepad1.x){
                stage = 1;
            }

            if(gamepad1.y){
                stage = 2;
            }

            if(gamepad1.b){
                stage = 3;
            }

            if(change.get(gamepad1.x || gamepad1.y || gamepad1.b)) {
                switch (stage) {
                    case 1:
                        stanchionPos = 0;
                        extenderPos = 0.75;
                        extenstionAccess = true;
                        break;

                    case 2:
                        stanchionPos = 0.5;
                        extenderPos = 0.5;
                        extenstionAccess = false;
                        break;

                    case 3:
                        stanchionPos = 1;
                        extenderPos = 1;
                        extenstionAccess = false;
                        break;
                }
            }

            if(extenstionAccess){
                extenderPos += (gamepad1.dpad_up? 0.005 : 0) + (gamepad1.dpad_down? -0.005 : 0);
                extenderPos = (extenderPos < 0? 0 : extenderPos > 1? 1 : extenderPos);
            }

            extender.setTargetPosition(extenderPos);
            stanchion.setTargetPosition(stanchionPos);

            extender.move();
            stanchion.move();

            hw.getSweeper().setPosition(sweeper);

        }

    }
}
