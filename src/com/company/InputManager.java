package com.company;

import java.io.*;

class InputManger {

    public BufferedWriter bufferedWriter;
    public FileWriter fileWriter;
    public FileReader fileReader;
    public BufferedReader bufferedReader;

    double[] leftFrontValues;
    double[] leftBackValues;
    double[] rightFrontValues;
    double[] rightBackValues;

    //double ly, rx, lx;

    //int lTicks, rTicks, sTicks;

    enum DriveType {
        h4, h3, norm4, norm2
    }

    boolean useGyro, useCV;

    int numDriveMotors, numAuxMotors, numServos;

    //int m1Ticks, m2Ticks, m3Ticks, m4Ticks, m5Ticks, m6Ticks, m7Ticks, m8Ticks;

    int[] mTicks = new int[numDriveMotors + numAuxMotors];
    int[] sPos = new int[numServos];

    int s1Pos, s2Pos, s3Pos, s4Pos, s5Pos, s6Pos, s7Pos, s8Pos, s9Pos, s10Pos, s11Pos, s12Pos;

    int gyroPos;

    int countLines, countReplays;

    public InputManger(DriveType type, int numAuxMotors, int numServos) {

        switch (type) {
            case h4: {

                numDriveMotors = 4;

            }
            case h3: {

                numDriveMotors = 3;

            }
            case norm4: {

                numDriveMotors = 4;

            }
            case norm2: {

                numDriveMotors = 2;

            }
        }

        this.numAuxMotors = numAuxMotors;
        this.numServos = numServos;

    }

    public void setupRecording(File file) {

        // Assume default encoding.
        fileWriter = null;

        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Always wrap FileWriter in BufferedWriter.
        bufferedWriter = new BufferedWriter(fileWriter);

    }

    public void writeInputs() {


        mTicks = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};


        String line = "";

        for(int i = 0; i < (mTicks.length); i++){

            line += mTicks[i] + ";";

        }

        for(int i = 0; i < (sPos.length); i++){

            line += sPos[i] + ";";

        }

        line += gyroPos + ";";

        try {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //dt.manualDrive(gamepad1);

    }

    public void setupPlayback(File file) {
        String[] tempString;
        String line;

        leftFrontValues = new double[50000];
        rightFrontValues = new double[50000];

        try {

            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {

                tempString = line.split(";");

                for(int i = 0; i < mTicks.length; i++){

                    mTicks[i] = (Integer.parseInt(tempString[i]));

                }

                for(int i = 0; i < sPos.length; i++){

                    sPos[i] = (Integer.parseInt(tempString[i + mTicks.length]));

                }

                gyroPos = Integer.parseInt(tempString[19]);

                countLines++;
            }

            bufferedReader.close();

            countReplays = 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void replayInputs() {
        while (countReplays <= countLines) {
            //dt.runMotorsIndiv(leftFrontValues[countReplays], rightFrontValues[countReplays], leftBackValues[countReplays], rightBackValues[countReplays]);
            countReplays++;
        }
        for(int i : mTicks){
            System.out.println(i);
        }
        for(int i : sPos){
            System.out.println(i);
        }
    }

    public void stopAndReset() {

        try {

            bufferedReader.close();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}