package org.firstinspires.ftc.teamcode.Autonomus;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private InputStream stream;
    private Scanner sc;
    private ArrayList<String> line;

    public FileReader(String s){

        stream = FileReader.class.getResourceAsStream(s);
        sc = new Scanner(stream);
        line = new ArrayList<>();

        while(true){

            try {
                line.add(sc.nextLine());
            } catch (NullPointerException e){
                break;
            }

        }


    }

    public ArrayList<String> getLines(){
        return line;
    }

}
