package org.firstinspires.ftc.teamcode.Autonomus;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;

public class Gold {

    private MotorController mc;
    private Functions f;
    private Hardware hw;
    private Telemetry tm;
    private Base self;

    public Gold(Base self){
        this.mc = self.getMc();
        this.f = self.getF();
        this.hw = self.getHw();
        this.tm = self.getTem();
        this.self = self;

    }

    public void execute(){
//        Move into the depot
        f.forward(5);

//        spin to claim
        f.right(180);

//        Claim
        hw.getSweeper().setPosition(0);

        self.sleep(2000);

        hw.getSweeper().setPosition(0.5);

    }
}
