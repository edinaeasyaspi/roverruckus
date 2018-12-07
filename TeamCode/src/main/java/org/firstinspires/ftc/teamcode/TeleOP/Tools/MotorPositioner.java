package org.firstinspires.ftc.teamcode.TeleOP.Tools;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorPositioner {
    private DcMotor motor;
    private double finalPower, currVel, tPosition, tVel, currPosition, lastPos, addPower, maxSpeed;
    private int accel, max, charge;
    
//    Constructor
    public MotorPositioner(DcMotor motor, int acceleration, int maxEncoderValue, double maxSpeed, boolean reversed){
        
        this.motor = motor;
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        accel = acceleration;
        max = maxEncoderValue;
        this.maxSpeed = maxSpeed;

//        Init all the variables
        finalPower = 0;
        currVel = 0;
        tVel = 0;
        lastPos = 0;
        addPower = 0;

//        This is if the wire of the motor is plugged in backwards
        charge = reversed? -1 : 1;
        
    }
    
    
//    This will determine where the motor will move to
    public void setTargetPosition(double position){
        tPosition = position;
    }

    public double getFinalPower() {
        return finalPower;
    }

    public double getCurrVel() {
        return currVel;
    }

    public double gettPosition() {
        return tPosition;
    }

    public double gettVel() {
        return tVel;
    }

    public double getCurrPosition() {
        return currPosition;
    }

    public double getLastPos() {
        return lastPos;
    }

    public double getAddPower() {
        return addPower;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxCount(int count){
        max = count;
    }

    public void move(){

//        Converts the position to a double (decimal) between 0 and 1
        currPosition = (double) motor.getCurrentPosition() / max;
//        What the speed the motor wants to move at
//        This means as the motor gets further from its target position,
//        it speeds up in order to get to the position it needs to be.
        tVel = (tPosition - currPosition);
//        Measures what speed the motor is moving at
        currVel = currPosition - lastPos;
//        Updates the lastPos variable for the speed reading in the next iteration
        lastPos = currPosition;
//        Makes sure that the motor is moving at the right speed
//        This is basically what we did above, but for velocity
//        We need this because we need to fight against forces like gravity
        addPower = (tVel - currVel) * accel;
//        Just the variable that combines the 2 velocity variables into one
        finalPower = (tVel + addPower) * charge;
//        Constrain the final power so the value is within -1 to 1
        finalPower = !(Math.abs(finalPower) > maxSpeed) ? finalPower : maxSpeed * (Math.abs(finalPower) / finalPower);
//        Set the motor to the final power
        motor.setPower(finalPower);
        
    }

}
