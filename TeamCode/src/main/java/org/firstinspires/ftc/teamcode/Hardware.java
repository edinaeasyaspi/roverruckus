package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
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

        /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         */
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);


        parameters.vuforiaLicenseKey = "Af2Jdgv/////AAABmTRZvnw8TEljipKgw/L2hiURA/QVfil6Xmalwf6NUArV6dJV+ASpcYtKxkQayGtLLJ1Z6llBXmiBpm3ljI2IgzWC6ThdevkJa9apNNA7f2HXXSIZPxbd5CSphIDO/rEdPezKJqvfg1gz6Fke73O4RdutniIplm5r/BMg+0v/j6KOEjuwUZOQ5iaAzSbKPjmM95X1W68PqMf0BDETNH6Znv8ZuvICS7/8UuWeAZkbQ7CsFc30/9louXQ+fD760zalcZo9sW24s4Fh6AkUFmur/hN8HOhnAM/mUVISl5MJwCEsWGyAbGozJDPgr1cWcl5DUVZJZVdwYMuAKFeDDZZpQgzgz5AEINO+XJJlQuu2MukZ";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vf = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables relicTrackables = this.vf.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

    }

//    What the robot will do when it is initiated
    public void init(){

    }

    public String getError(){
        return errorMsg;
    }

}
