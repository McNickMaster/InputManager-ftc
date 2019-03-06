package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        InputManger inputManger = new InputManger(InputManger.DriveType.h4, 3, 2);

        inputManger.setupRecording(new File("test1.txt"));
        inputManger.writeInputs();
        inputManger.setupPlayback(new File("test1.txt"));
        inputManger.replayInputs();

    }
}