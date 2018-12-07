package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.Vuforia;


@Autonomous(name="Main")
public class Base extends LinearOpMode {

    private Hardware hw;
    private Vuforia vf;
    private MotorController mc;
    private double latchPos;

    @Override
    public void runOpMode() throws InterruptedException {

        hw = new Hardware(hardwareMap);
        vf = new Vuforia(hardwareMap);

//        Set all the encoders to 0
        hw.getlDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getcDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getrDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getlDrive().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hw.getcDrive().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hw.getrDrive().setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        Define and start the hanger positioner
        mc = new MotorController(hw);
        mc.start();
        latchPos = 0;
        mc.setLatchPos(0);

//        Create a new instance of the Functions class so we can control the robot easily
        Functions f = new Functions(hw);

//        Make sure the phones don't disconnect while waiting for start
        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addLine("Sending manual heartbeat");
            telemetry.update();
        }

//        Unlatch
        setLatchPos(1);

//        Set to driving mode
        setLatchPos(0.25);

//        Line up with the lander
        f.reverse(2);

//        Set to strafing mode
        setLatchPos(0);

//        Move away from the lander
        f.strafeLeft(8);

//        Driving mode
        setLatchPos(0.25);

//        Turn to face minerals
        f.left(90);

//        Strafing mode
        setLatchPos(0);

//        Move to the left side of the minerals
        f.strafeLeft(5);

//        Set the strafe wheel so it can line up with the gold mineral
        hw.getcDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getcDrive().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

//        Activate tensorflow
        vf.activate();

//        Set variables for sampling
        double position = 0;
        double goldX;
        double power;

//        This will run until the code is stopped, or the robot has moved to the other end of the minerals
        while(!opModeIsActive() && !isStopRequested() && position > (-33 * 126.6)){
//            This is to keep track of how far the robot has moved
            position = hw.getcDrive().getCurrentPosition();
//            Set goldX to the gold mineral x position on the camera, -500 which makes the origin in the center of the screen
            goldX = vf.getGoldX() - 500;

//            Testing if the robot can see the gold
            if(goldX == Double.NaN){
//                Keep driving by all the minerals
                power = -0.5;
            } else{
//                Set power to align with the gold based on how offset the robot is
                power = goldX / -700.0d;

//                If the robot is aligned with the gold mineral
                if(Math.abs(goldX) < 50){
//                    Stop strafing
                    hw.getcDrive().setPower(0);
//                    Driving mode
                    setLatchPos(0.25);
//                    Bump mineral
                    f.forward(5);
//                    Wait for momentum to stop
                    sleep(100);
//                    Go back to original position
                    f.reverse(5);
//                    Exit out of the while loop, since we have successfully sampled
                    break;
                }
            }

//            Set the power of the motor from the hardware
            hw.getcDrive().setPower(power);
        }

        mc.threadStop();

    }

    public void setLatchPos(double pos){
//        Set the latch position in the MotorController class
        mc.setLatchPos(pos);
//        Wait until the latch has stopped
        sleep(Math.round(Math.abs(pos - latchPos) * 1500));
//        Update the current position value
        latchPos = pos;
    }
}
