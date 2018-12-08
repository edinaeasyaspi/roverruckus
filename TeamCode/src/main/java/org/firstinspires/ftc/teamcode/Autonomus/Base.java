package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.Vuforia;


@Autonomous(name = "Main")
public class Base extends LinearOpMode {

    private Hardware hw;
    private Vuforia vf;
    private String side;
    private MotorController mc;
    private Functions f;
    private double latchPos;

    public Functions getF() {
        return f;
    }

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
        f = new Functions(hw);

        side = "none";

//        Make sure the phones don't disconnect while waiting for start
        while (!opModeIsActive() && !isStopRequested() || side.equals("none")) {
            telemetry.addLine("Sending manual heartbeat");

            if(side.equals("none")){
                telemetry.addLine("Select a side. 'a' = gold and 'b' = silver");

                if(gamepad1.a){
                    side = "gold";
                }

                if(gamepad1.b){
                    side = "silver";
                }

            } else{
                telemetry.addLine("All good, waiting for start");
            }

            telemetry.update();
        }

//        Unlatch
        print("Unlatching");
        setLatchPos(1);

//        Move away from hook
        f.forward(4);

//        Lower hook
        setLatchPos(0.25);

//        Line up with the lander
        f.reverse(4);

//        Set to strafing mode
        setLatchPos(0);

//        Move away from the lander
        print("Positioning");
        f.strafeLeft(10);

//        Driving mode
        setLatchPos(0.25);

//        Turn to face minerals
        f.left(90);

//        Strafing mode
        setLatchPos(0);

//        Move to the left side of the minerals
        f.strafeLeft(10);


//        Set the strafe wheel so it can line up with the gold mineral
        print("Resetting encoders");
        hw.getcDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getcDrive().setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        Activate tensorflow
        print("Starting vuforia");
        vf.activate();

//        Set variables for sampling
        int position = 0;
        boolean isFound = false;
        double goldX;
        double power;
        int foundFrames = 0;

//        This will run until the code is stopped, or the robot has moved to the other end of the minerals
        print("Scanning");

        while (opModeIsActive() && !isStopRequested() && position > (-40 * 126.6)) {
//            This is to keep track of how far the robot has moved
            position = hw.getcDrive().getCurrentPosition();
//            Set goldX to the gold mineral x position on the camera, -500 which makes the origin in the center of the screen
            goldX = vf.getGoldX() - 500;
            isFound = (isFound) || vf.isGoldFound();

            telemetry.addData("Gold x", goldX);

//            Testing if the robot can see the gold
            if (!isFound) {
//                Keep driving by all the minerals
                telemetry.addLine("Cannot see");
                power = -0.25;
                foundFrames = 0;
            } else {
                telemetry.addLine("Aligning");
//                Set power to align with the gold based on how offset the robot is
                power = goldX / -700.0d;

                foundFrames += 1;

//                If the robot is aligned with the gold mineral
                if (Math.abs(goldX) < 50 && foundFrames > 100) {
                    print("Hitting");
//                    Stop strafing
                    hw.getcDrive().setPower(0);
//                    Driving mode
                    setLatchPos(0.25);
//                    Bump mineral
                    f.forward(10, 0.5);
//                    Wait for momentum to stop
                    sleep(100);
//                    Return
                    if(side.equals("silver")) {
                        f.reverse(7, 0.5);
                    }
//                    Exit out of the while loop, since we have successfully sampled
                    break;
                }
            }

            telemetry.update();

//            Set the power of the motor from the hardware
            hw.getcDrive().setPower(power);
        }

        vf.deactivate();

        print("Done sampling");

        sleep(100);

//        Split between the 2 codes
        switch(side){
            case "gold":
                new Gold(this).execute();
                break;
            case "silver":
                new Sliver(this, position).execute();
                break;
        }

        mc.threadStop();


    }

    public MotorController getMc() {
        return mc;
    }

    public Hardware getHw() {

        return hw;
    }

    public Telemetry getTem(){
        return telemetry;
    }

    public void setLatchPos(double pos) {
//        Set the latch position in the MotorController class
        mc.setLatchPos(pos);
//        Wait until the latch has stopped
        sleep(Math.round(Math.abs(pos - latchPos) * 1700));
//        Update the current position value
        latchPos = pos;
    }

    public void print(String s){
        telemetry.addLine(s);
        telemetry.update();
    }
}
