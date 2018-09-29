package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Hardware {
    //    This is going to define all the hardware that will be used in the code
    public ColorSensor sampler;


//    What will happen when there is a new object if this class
    public Hardware(HardwareMap hwMap){
//        Assign all the object to the actual components on the robot
        sampler = hwMap.colorSensor.get("sampler");
    }

//    What the robot will do when it is initiated
    public void init(){

    }

}
