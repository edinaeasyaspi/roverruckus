/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="ColorSensor", group="Robot")
//@Disabled
public class ColorSensorTest extends LinearOpMode {

//    Links the hardware to the main class and gets hardwareMap from LinearOpMode
    private Hardware hw;


//    All the variables and fields that will be used in runOpMode()
    private int R, G, B, A, r, g, b, a;
    private String sight;


//    This is what is going to happen when the code starts
    public void runOpMode(){

        hw = new Hardware(hardwareMap);
        telemetry.addData("Error", hw.getError());

        r = 0;
        g = 0;
        b = 0;
        a = 0;
        R = 0;
        G = 0;
        B = 0;
        A = 0;

        sight = "air";
//        Waits until driver presses play

        telemetry.addLine("Waiting...");
        telemetry.update();

        waitForStart();

        telemetry.clearAll();

        hw.init();

        telemetry.addLine("Calibrating");
        telemetry.update();

        for(int i = 0 ; i < 10; i++){
            R += hw.sampler.red();
            G += hw.sampler.green();
            B += hw.sampler.blue();
            A += hw.sampler.alpha();
        }

        R /= 10;
        G /= 10;
        B /= 10;
        A /= 10;

        while (opModeIsActive()){
//            Sets the colors for what the sampler sees

            r = hw.sampler.red();
            g = hw.sampler.green();
            b = hw.sampler.blue();
            a = hw.sampler.alpha();

//            Displays what the sampler sees in contrast to the air
           telemetry.addData("Red",  R - r);
           telemetry.addData("Green", G - g);
           telemetry.addData("Blue", B - b);
           telemetry.addData("Alpha", A - a);

//           Tells the difference between white and nothing

            if(Math.abs(R - r) > 10 || Math.abs(G - g) > 10 || Math.abs(B - b) > 10){
                sight = "object";
            } else{
                sight = "air";
            }
            telemetry.addData("Sight", sight);
            telemetry.update();
        }

    }

}
