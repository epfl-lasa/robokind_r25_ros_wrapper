package com.github.rosjava.my_pub_sub_tutorial.Robokind;
import org.robokind.api.motion.messaging.RemoteRobot;
import org.robokind.api.speech.messaging.RemoteSpeechServiceClient;

import static org.robokind.api.motion.Robot.*;
import static org.robokind.client.basic.RobotJoints.LEFT_HAND_GRASP;
import static org.robokind.client.basic.RobotJoints.LEFT_SHOULDER_PITCH;
import static org.robokind.client.basic.RobotJoints.LEFT_SHOULDER_ROLL;
import static org.robokind.client.basic.RobotJoints.LEFT_WRIST_YAW;

import org.robokind.api.common.position.NormalizedDouble;
import org.robokind.api.motion.Joint;
import org.robokind.api.motion.Robot.JointId;
import org.robokind.api.motion.Robot.RobotPositionHashMap;
import org.robokind.client.basic.Robokind;

public class InteractionDemo {
    // class variables
    private static RobotPositionMap myGoalPositions;
    private static RemoteRobot myRobot;
    private static int timeFrame;
    private static RemoteSpeechServiceClient mySpeaker;
    public static void main(String[] args) throws InterruptedException {
    	String robotID = "myRobot";
        String robotIP = "192.168.2.3";
        // set addresses
        SetSettings settings = new SetSettings(robotID, robotIP);
        // make connection
        myRobot = Robokind.connectRobot();
        mySpeaker = Robokind.connectSpeechService();
        // check if robot is connected
        if (myRobot.isConnected()) {
        	
        	Expression expression = new Expression();
        	expression.setExpressionJoints(myRobot);
        	
        	Letters letters = new Letters();
        	
//    		mySpeaker.speak("Hi! Would you like to play a Letter game?");
//    		Robokind.sleep(900);
//    		expression.smile(700, myRobot);
//    		//expression.blink(500, myRobot);
//    		Robokind.sleep(400);
//    		mySpeaker.speak("I will point you to the rectangels and squares placed on the table and you have to construct letters from them");
//    		Robokind.sleep(3000);
//    		mySpeaker.speak("Lets start the game!");
//    		Robokind.sleep(800);
//        	letters.describeLetter("T", 12000, mySpeaker, myRobot);

            double default_neck_pitch_position = 0.4;
            double left_shoulder_pitch_min = 0.185;
            double left_shoulder_pitch_max = 1;
            
            // object position with respect to the robot
            double x = 20;
            double z = 30;
            double y = 50;
            
            double d = Math.sqrt(Math.pow(x,2) + Math.pow(z,2)); 
            
            JointId neck_pitch_Id = new JointId(myRobot.getRobotId(), new Joint.Id(200));
            JointId neck_yaw_Id = new JointId(myRobot.getRobotId(), new Joint.Id(202));
            JointId brows_Id = new JointId(myRobot.getRobotId(), new Joint.Id(300));
            JointId left_elbow_yaw_Id = new JointId(myRobot.getRobotId(), new Joint.Id(410));
            JointId left_elbow_pitch_Id = new JointId(myRobot.getRobotId(), new Joint.Id(411));
            JointId left_shoulder_pitch_Id = new JointId(myRobot.getRobotId(), new Joint.Id(400));
            JointId left_shoulder_roll_Id = new JointId(myRobot.getRobotId(), new Joint.Id(401));
            JointId left_wrist_yaw_Id = new JointId(myRobot.getRobotId(), new Joint.Id(420));
            JointId left_hand_grasp_Id = new JointId(myRobot.getRobotId(), new Joint.Id(421));

            
            double angle_v = Math.toDegrees(Math.atan(d/y));
            double angle_h = Math.toDegrees(Math.atan(x/z));
            
            System.out.println(angle_h);
            
            double brows_value =  angle_v / 180;
            double neck_pitch_value = default_neck_pitch_position * angle_v / 90;
            double neck_yaw_value = (90 + angle_h) / 180;
            
            System.out.println(neck_yaw_value);
            Robokind.sleep(400);
            myGoalPositions = new RobotPositionHashMap();
		    myGoalPositions.put(neck_pitch_Id, new NormalizedDouble(neck_yaw_value));
            myGoalPositions.put(neck_yaw_Id, new NormalizedDouble(neck_pitch_value));;
		    myGoalPositions.put(brows_Id, new NormalizedDouble(brows_value));
		    myRobot.move(myGoalPositions, 400);

		   
		    myGoalPositions.put(left_elbow_yaw_Id, new NormalizedDouble(0.3));
		    myGoalPositions.put(left_shoulder_pitch_Id, new NormalizedDouble(0.8));
		    
		    myGoalPositions.put(left_wrist_yaw_Id, new NormalizedDouble(0));
		    myRobot.move(myGoalPositions, 400);
		    Robokind.sleep(800);
		    
//		    myGoalPositions = myRobot.getDefaultPositions();
//          myRobot.move(myGoalPositions, 1000);

        	
            ///////////////////////////////////////////
            /// DISCONNECT AND EXIT
            ///////////////////////////////////////////
            
            Robokind.disconnect();
            System.exit(0);
        }
    }  
}