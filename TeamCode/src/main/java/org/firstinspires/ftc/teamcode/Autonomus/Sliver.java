package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;

public class Sliver {

    private MotorController mc;
    private Functions f;
    private Hardware hw;
    private Telemetry tm;
    private Base self;
    private double cPos;

    public Sliver(Base self, double position){
        this.mc = self.getMc();
        this.f = self.getF();
        this.hw = self.getHw();
        this.tm = self.getTem();
        this.cPos = position;
        this.self = self;

    }

    public void execute(){

//        Reset position
        self.print("Returning");
        hw.getcDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getcDrive().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hw.getcDrive().setPower(1);
        while(hw.getcDrive().isBusy()){}

//        Go to left wall
        self.print("Claiming");
        f.strafeLeft(45);

//        Drive mode
        self.setLatchPos(0.25);

//        Align
        f.right(45);

//        Move to depot
        f.reverse(40, 2);

//        Turn on sweeper
        hw.getSweeper().setPosition(0);

//        Wait
        self.sleep(1000);

//        Turn off sweeper
        hw.getSweeper().setPosition(0.5);
    }
}
