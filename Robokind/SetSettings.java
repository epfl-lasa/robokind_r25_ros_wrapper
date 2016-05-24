package com.github.rosjava.my_pub_sub_tutorial.Robokind;

import org.robokind.client.basic.UserSettings;

public class SetSettings {
    // class variables
    private static String robotID;
    private static String robotIP;
    
    /** 
     * Default constructor
     */
    public SetSettings() {
        
    }
    
    /**
     * Sets all addresses for connecting
     * @param robotIP the IP address of the robot
     * @param myRobot the remote Robot
     */
    public SetSettings(String robotID, String robotIP) {
        this.robotIP = robotIP;
        this.robotID = robotID;
        // set respective addresses
        UserSettings.setRobotId(robotID);
        UserSettings.setRobotAddress(robotIP);
        UserSettings.setAnimationAddress(robotIP);
        UserSettings.setSpeechAddress(robotIP);
        UserSettings.setSensorAddress(robotIP);
        UserSettings.setAccelerometerAddress(robotIP);
        UserSettings.setGyroscopeAddress(robotIP);
        UserSettings.setCompassAddress(robotIP);
        UserSettings.setCameraAddress(robotIP);
        UserSettings.setImageRegionAddress(robotIP);
        UserSettings.setImageRegionId("0");
    }
}