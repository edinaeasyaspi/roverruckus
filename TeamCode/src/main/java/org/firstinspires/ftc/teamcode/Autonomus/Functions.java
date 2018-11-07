package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware;

public class Functions {
    
    Hardware hw;

    private static final double COUNTS_PER_INCH = 100;
    private static final double COUNTS_PER_DEGREE = 100;
    private static final double COUNTS_PER_STRAFE_INCH = 100;
    private static final double MAX_SPEED = 1;

    private int leftEncoderTarget;
    private int rightEncoderTarget;
    private int centerEncoderTarget;

    private double lDrive, cDrive, rDrive;

    public static double getMaxSpeed() {
        return MAX_SPEED;
    }

    public static double getCountsPerInch() {
        return COUNTS_PER_INCH;
    }

    public static double getCountsPerDegree() {
        return COUNTS_PER_DEGREE;
    }

    public static double getCountsPerStrafeInch() {
        return COUNTS_PER_STRAFE_INCH;
    }

    public Functions(Hardware hw){
        this.hw = hw;
    }
    
    public void forward(double inches){
        resetEncoders();
        leftEncoderTarget = (int)Math.round(inches * COUNTS_PER_INCH);
        rightEncoderTarget = (int)Math.round(inches * COUNTS_PER_INCH);


//        Drive until reached target, gradually slow down before target
        while((Math.abs(leftEncoderTarget - hw.getlDrive().getCurrentPosition()) > 10) || (Math.abs(rightEncoderTarget - hw.getrDrive().getCurrentPosition()) > 10)){

            lDrive = (leftEncoderTarget - hw.getlDrive().getCurrentPosition()) * 0.00001;
//            Limit speed
            lDrive = !(Math.abs(lDrive) > MAX_SPEED) ? lDrive : MAX_SPEED * Math.abs(lDrive) / lDrive;

            rDrive = (rightEncoderTarget - hw.getrDrive().getCurrentPosition()) * 0.00001;
//            Limit speed
            rDrive = !(Math.abs(rDrive) > MAX_SPEED) ? rDrive : MAX_SPEED * Math.abs(rDrive) / rDrive;

            hw.getlDrive().setPower(lDrive);
            hw.getrDrive().setPower(rDrive);

        }

        hw.getlDrive().setPower(0);
        hw.getrDrive().setPower(0);

    }

    public void reverse(double inches){
        resetEncoders();
        leftEncoderTarget = (int)Math.round(inches * COUNTS_PER_INCH) * -1;
        rightEncoderTarget = (int)Math.round(inches * COUNTS_PER_INCH) * -1;


        //        Drive until reached target, gradually slow down before target
        while((Math.abs(leftEncoderTarget - hw.getlDrive().getCurrentPosition()) > 10) || (Math.abs(rightEncoderTarget - hw.getrDrive().getCurrentPosition()) > 10)){

            lDrive = (leftEncoderTarget - hw.getlDrive().getCurrentPosition()) * 0.00001;
//            Limit speed
            lDrive = !(Math.abs(lDrive) > MAX_SPEED) ? lDrive : MAX_SPEED * Math.abs(lDrive) / lDrive;

            rDrive = (rightEncoderTarget - hw.getrDrive().getCurrentPosition()) * 0.00001;
//            Limit speed
            rDrive = !(Math.abs(rDrive) > MAX_SPEED) ? rDrive : MAX_SPEED * Math.abs(rDrive) / rDrive;

            hw.getlDrive().setPower(lDrive);
            hw.getrDrive().setPower(rDrive);

        }

        hw.getlDrive().setPower(0);
        hw.getrDrive().setPower(0);

    }
    
    public void right(double degrees){

        leftEncoderTarget = (int)Math.round(degrees * COUNTS_PER_DEGREE);
        rightEncoderTarget = (int)Math.round(degrees * COUNTS_PER_DEGREE) * -1;


//      Drive until reached target, gradually slow down before target
        while((Math.abs(leftEncoderTarget - hw.getlDrive().getCurrentPosition()) > 10) || (Math.abs(rightEncoderTarget - hw.getrDrive().getCurrentPosition()) > 10)){

            lDrive = (leftEncoderTarget - hw.getlDrive().getCurrentPosition()) * 0.00001;
//            Limit speed
            lDrive = !(Math.abs(lDrive) > MAX_SPEED) ? lDrive : MAX_SPEED * Math.abs(lDrive) / lDrive;

            rDrive = (rightEncoderTarget - hw.getrDrive().getCurrentPosition()) * 0.00001;
//            Limit speed
            rDrive = !(Math.abs(rDrive) > MAX_SPEED) ? rDrive : MAX_SPEED * Math.abs(rDrive) / rDrive;

            hw.getlDrive().setPower(lDrive);
            hw.getrDrive().setPower(rDrive);

        }

        hw.getlDrive().setPower(0);
        hw.getrDrive().setPower(0);

    }
    
    public void left(double degrees){
        resetEncoders();

        leftEncoderTarget = (int)Math.round(degrees * COUNTS_PER_DEGREE) * -1;
        rightEncoderTarget = (int)Math.round(degrees * COUNTS_PER_DEGREE);

//      Drive until reached target, gradually slow down before target
        while((Math.abs(leftEncoderTarget - hw.getlDrive().getCurrentPosition()) > 10) || (Math.abs(rightEncoderTarget - hw.getrDrive().getCurrentPosition()) > 10)){

            lDrive = (leftEncoderTarget - hw.getlDrive().getCurrentPosition()) * 0.00001;
//            Limit speed
            lDrive = !(Math.abs(lDrive) > MAX_SPEED) ? lDrive : MAX_SPEED * Math.abs(lDrive) / lDrive;

            rDrive = (rightEncoderTarget - hw.getrDrive().getCurrentPosition()) * 0.00001;
//            Limit speed
            rDrive = !(Math.abs(rDrive) > MAX_SPEED) ? rDrive : MAX_SPEED * Math.abs(rDrive) / rDrive;

            hw.getlDrive().setPower(lDrive);
            hw.getrDrive().setPower(rDrive);

        }

        hw.getlDrive().setPower(0);
        hw.getrDrive().setPower(0);

    }
    
    public void strafeRight(double inches){
        resetEncoders();


    }

    public void strafeLeft(double inches){
        resetEncoders();

    }
    
    public void interpretLine(String currentLine){
        switch (currentLine.split(" ")[0]) {

            case "forward":
                forward(Double.parseDouble(currentLine.split(" ")[1]));
                break;

            case "reverse":
                reverse(Double.parseDouble(currentLine.split(" ")[1]));
                break;
            case "right":
                right(Double.parseDouble(currentLine.split(" ")[1]));
                break;

            case "left":
                left(Double.parseDouble(currentLine.split(" ")[1]));
                break;

            case "strafeRight":
                strafeRight(Double.parseDouble(currentLine.split(" ")[1]));
                break;

            case "strafeLeft":
                strafeLeft(Double.parseDouble(currentLine.split(" ")[1]));
                break;
        }
    }

    private void resetEncoders(){
        hw.getlDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getcDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getrDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hw.getlDrive().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hw.getcDrive().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hw.getrDrive().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
