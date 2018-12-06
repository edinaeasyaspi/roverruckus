package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;

public class Vuforia {

//    Customizable fields for recognition
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "Af2Jdgv/////AAABmTRZvnw8TEljipKgw/L2hiURA/QVfil6Xmalwf6NUArV6dJV+ASpcYtKxkQayGtLLJ1Z6llBXmiBpm3ljI2IgzWC6ThdevkJa9apNNA7f2HXXSIZPxbd5CSphIDO/rEdPezKJqvfg1gz6Fke73O4RdutniIplm5r/BMg+0v/j6KOEjuwUZOQ5iaAzSbKPjmM95X1W68PqMf0BDETNH6Znv8ZuvICS7/8UuWeAZkbQ7CsFc30/9louXQ+fD760zalcZo9sW24s4Fh6AkUFmur/hN8HOhnAM/mUVISl5MJwCEsWGyAbGozJDPgr1cWcl5DUVZJZVdwYMuAKFeDDZZpQgzgz5AEINO+XJJlQuu2MukZ";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private double goldX;
    private boolean goldFound;

    public Vuforia(HardwareMap hwMap){

//        Vuforia
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.

//       tfod
        int tfodMonitorViewId = hwMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hwMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    public void activate(){
        tfod.activate();
        goldFound = false;
        goldX = getGoldX();
    }

    public void deactivate(){
        tfod.deactivate();
    }

    public double getGoldX(){
//        Assign what TFOD is seeing to an ArrayList
        ArrayList<Recognition> getObjects = (ArrayList<Recognition>)(tfod.getRecognitions());

//        Makes sure there are objects
            if(getObjects != null && getObjects.size() > 0) {

//                Iterate through each new object that TFOD has detected
                for (Recognition object : getObjects) {
                    if (object.getLabel().equals(LABEL_GOLD_MINERAL) && object.getTop() < 500) {
//                        This is when the object detected is a gold mineral

                        goldFound = true;
//                        Set our variable goldX to what our updated gold mineral is
                        goldX = (double) object.getLeft();
//                        Break out of the for loop since we have already found the gold mineral
                        return goldX;
                    }
                }
            }


        return Double.NaN;
    }

    public ArrayList<Recognition> getObjects(){
            return (ArrayList<Recognition>)(tfod.getRecognitions());
    }
}
