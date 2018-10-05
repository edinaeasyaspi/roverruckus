package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Hardware {
    //    This is going to define all the hardware that will be used in the code
    public ColorSensor sampler;
    private String errorMsg;


//    What will happen when there is a new object if this class
    public Hardware(HardwareMap hwMap){
        errorMsg = "";

//        Assign all the object to the actual components on the robot
        try {
            sampler = hwMap.colorSensor.get("sampler");
        } catch (NullPointerException e){
            errorMsg = "Cannot get hardware";
        }

        if (errorMsg.equals("")){
            errorMsg = "No errors";
        }
    }

//    What the robot will do when it is initiated
    public void init(){

    }

    public String getError(){
        return errorMsg;
    }

}
