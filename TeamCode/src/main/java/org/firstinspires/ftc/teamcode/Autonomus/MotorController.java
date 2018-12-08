package org.firstinspires.ftc.teamcode.Autonomus;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleOP.Tools.MotorPositioner;

public class MotorController extends Thread {

    private MotorPositioner stanchion, latch, extender;
    private double stanchionPos, latchPos, extenderPos;
    private boolean active;

    public MotorController(Hardware hw) {

        stanchionPos = 0;
        extenderPos = 0;
        latchPos = 0;
        active = true;


//        Define all our motors
        stanchion = new MotorPositioner(
                hw.getStanchion(),
                20,
                6300,
                0.5,
                false
        );

//        This is how we will maintain the motor position for the latch
        latch = new MotorPositioner(
                hw.getHanger(), // The motor that we want to control
                40, // This number should usually be somewhere in between 50 and 0
                3000, // How far the motor actually goes in encoder counts
                1, // The max speed that the motor is going to go
                false // Whether the motor is reversed or not
        );
        extender = new MotorPositioner(
                hw.getExtender(),
                30,
                12500,
                1,
                false
        );

    }

    public double getStanchionPos() {
        return stanchionPos;
    }

    public void setStanchionPos(double stanchionPos) {
        this.stanchionPos = stanchionPos;
    }

    public double getLatchPos() {
        return latchPos;
    }

    public void setLatchPos(double latchPos) {
        this.latchPos = latchPos;
    }

    public double getExtenderPos() {
        return extenderPos;
    }

    public void setExtenderPos(double extenderPos) {
        this.extenderPos = extenderPos;
    }

    public void run() {

        while (active) {
//            Set all the target positions
            stanchion.setTargetPosition(stanchionPos);
            extender.setTargetPosition(extenderPos);
            latch.setTargetPosition(latchPos);

//            Move all the motors
            stanchion.move();
            extender.move();
            latch.move();
        }

    }

    public void threadStop() {
        active = false;
    }
}
