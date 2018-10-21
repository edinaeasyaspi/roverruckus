package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


public class Hardware {
    //    This is going to define all the hardware that will be used in the code
    public ColorSensor sampler;
    public VuforiaLocalizer vf;

    public DcMotor getlDrive() {
        return lDrive;
    }

    public DcMotor getcDrive() {
        return cDrive;
    }

    public DcMotor getrDrive() {
        return rDrive;
    }

    private DcMotor lDrive, cDrive, rDrive;



    private String errorMsg;

//    What will happen when there is a new object if this class
    public Hardware(HardwareMap hwMap){
        errorMsg = "";

//        Assign all the object to the actual components on the robot
        try {

//            sampler = hwMap.colorSensor.get("sampler");
            lDrive = hwMap.dcMotor.get("lDrive");
            cDrive = hwMap.dcMotor.get("cDrive");
            rDrive = hwMap.dcMotor.get("rDrive");

//            Set all the motor powers to 0 and assign preferences
            lDrive.setPower(0);
            lDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            lDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            cDrive.setPower(0);
            cDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            cDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            cDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            rDrive.setPower(0);
            rDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            rDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        } catch (Exception e){
            errorMsg = "Could not get hardware\nError:\n" + e.toString();
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
