package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Hardware {
    //    This is going to define all the hardware that will be used in the code
    public ColorSensor sampler;

    public DcMotor getlDrive() {
        return lDrive;
    }

    public DcMotor getcDrive() {
        return cDrive;
    }

    public DcMotor getrDrive() {
        return rDrive;
    }

    private DcMotor lDrive, cDrive, rDrive, hanger, stanchion, extender;
    private Servo sweeper, sorter;


    private String errorMsg;

    public DcMotor getHanger() {
        return hanger;
    }

    public Servo getSweeper() {
        return sweeper;
    }

    public DcMotor getStanchion() {
        return stanchion;
    }

    public DcMotor getExtender() {
        return extender;
    }

    public Servo getSorter() {
        return sorter;
    }

    //    What will happen when there is a new object if this class
    public Hardware(HardwareMap hwMap) {
        errorMsg = "";

//        Assign all the object to the actual components on the robot
        try {

            lDrive = hwMap.dcMotor.get("lDrive");
            cDrive = hwMap.dcMotor.get("cDrive");
            rDrive = hwMap.dcMotor.get("rDrive");

            hanger = hwMap.dcMotor.get("hanger");
            stanchion = hwMap.dcMotor.get("stanchion");
            extender = hwMap.dcMotor.get("extender");
            sweeper = hwMap.servo.get("sweeper");
            sorter = hwMap.servo.get("sorter");

//            Set all the motor powers to 0 and assign preferences
            lDrive.setPower(0);
            lDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            lDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            cDrive.setPower(0);
            cDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            cDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            cDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            cDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            rDrive.setPower(0);
            rDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            rDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            hanger.setPower(0);
            hanger.setDirection(DcMotorSimple.Direction.REVERSE);
            hanger.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hanger.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            hanger.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            extender.setPower(0);
            extender.setDirection(DcMotorSimple.Direction.FORWARD);
            extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            stanchion.setPower(0);
            stanchion.setDirection(DcMotorSimple.Direction.FORWARD);
            stanchion.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            stanchion.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            stanchion.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            sweeper.setPosition(0.5);


        } catch (Exception e) {
            errorMsg = "Could not get hardware\nError:\n" + e.toString();
        }

        if (errorMsg.equals("")) {
            errorMsg = "No errors";
        }

    }

    public String getError() {
        return errorMsg;
    }

}
