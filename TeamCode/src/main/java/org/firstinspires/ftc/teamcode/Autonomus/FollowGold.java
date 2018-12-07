package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.Vuforia;


@Autonomous(name="FollowGold")
public class FollowGold extends LinearOpMode {

    private double cDrive;
    private Hardware hw;
    private Vuforia vf;

    @Override
    public void runOpMode() throws InterruptedException {

        cDrive = 0;

        hw = new Hardware(hardwareMap);
        vf = new Vuforia(hardwareMap);

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addLine("Maintaining manual heartbeat");
            telemetry.update();
        }

        vf.activate();

        double goldX;

        while(opModeIsActive()){
//            The method is called once and stored to a variable, so it doesn't have to be called again

            goldX = vf.getGoldX() - 500;

            if(goldX != Double.NaN){
                cDrive = goldX / -700.0d;
            } else{
                cDrive = 0;
            }

            telemetry.addLine("Maintaining manual heartbeat");
            telemetry.addData("Base X position", goldX);
            telemetry.update();

            hw.getcDrive().setPower(cDrive);
        }

        vf.deactivate();

    }
}
