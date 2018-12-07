package org.firstinspires.ftc.teamcode.TeleOP.Tools;

public class Button {

    private boolean inter;

    public Button(){
        inter = false;
    }

    public boolean get(boolean statement){

//        What this class does is only return true for one frame when a boolean becomes true
//        Sort of like a mono-stable circuit
        if(statement && !inter){
            inter = true;
            return true;
        } else if(!statement){
            inter = false;
            return false;
        }

        return false;
    }

}
