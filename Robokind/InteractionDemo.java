package com.github.rosjava.my_pub_sub_tutorial.Robokind;
import org.robokind.api.motion.messaging.RemoteRobot;
import org.robokind.api.speech.messaging.RemoteSpeechServiceClient;

import static org.robokind.api.motion.Robot.*;

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
        	
    		mySpeaker.speak("Hi! Would you like to play a Letter game?");
    		Robokind.sleep(900);
    		expression.smile(700, myRobot);
    		//expression.blink(500, myRobot);
    		Robokind.sleep(400);
    		mySpeaker.speak("I will point you to the rectangels and squares placed on the table and you have to construct letters from them");
    		Robokind.sleep(3000);
    		mySpeaker.speak("Lets start the game!");
    		Robokind.sleep(800);
        	letters.describeLetter("T", 12000, mySpeaker, myRobot);

        	
            // set up expression joints

//            
//            int column = 4;
//            int rows = 3;
//            
//            double unit_column_value = (1.0 / column);
//            double unit_row_value = (1 / rows);
            
//            [column -3, row - 1]
//            
//            		
//            double column_position =  (4 * unit_column_value) - (unit_column_value / 2);
//            double row_position =  (3 * unit_row_value) - (unit_row_value / 2);
//            System.out.println(column_position);
//            System.out.println(row_position);
//            
//            myGoalPositions = new RobotPositionHashMap();
//			JointId neck_yaw_Id = new JointId(myRobot.getRobotId(), new Joint.Id(202));
//			JointId neck_pitch_Id = new JointId(myRobot.getRobotId(), new Joint.Id(200));
//			JointId eyelids_Id = new JointId(myRobot.getRobotId(), new Joint.Id(301));
//			JointId brows_Id = new JointId(myRobot.getRobotId(), new Joint.Id(300));
//			JointId eyes_pitch_Id = new JointId(myRobot.getRobotId(), new Joint.Id(311));
//			

//		    myGoalPositions.put(brows_Id, new NormalizedDouble(row_position));
//		    myRobot.move(myGoalPositions, 400);
//		    
//		    myGoalPositions.put(neck_yaw_Id, new NormalizedDouble(column_position));
//		    myRobot.move(myGoalPositions, 500);
//
//		    myGoalPositions.put(neck_pitch_Id, new NormalizedDouble(0.8));
//		    myRobot.move(myGoalPositions, 400);
//		    myGoalPositions.put(neck_yaw_Id, new NormalizedDouble(0.1));
//		    myRobot.move(myGoalPositions, 400);
//		    myGoalPositions.put(brows_Id, new NormalizedDouble(0.1));
//		    myRobot.move(myGoalPositions, 400);
//		    myGoalPositions.put(eyelids_Id, new NormalizedDouble(0.8));
//		    myRobot.move(myGoalPositions, 400);
//		    
//		    myGoalPositions.put(eyelids_Id, new NormalizedDouble(unit_row_value));
//		    myRobot.move(myGoalPositions, 400);
//
//		    myGoalPositions.put(eyes_pitch_Id, new NormalizedDouble(unit_row_value));
//		    myRobot.move(myGoalPositions, 400);
			
//		    Robokind.sleep(200);
		    
//            Expression expression = new Expression();
//            expression.setExpressionJoints(myRobot);
            
            // expression test
//            expression.lookDown(400, myRobot);
//            Robokind.sleep(200);
//            expression.gentleLookUp(400, myRobot);
//            Robokind.sleep(200);
            
//	          expression.lookRight(500, myRobot);
//	          Robokind.sleep(200);
//	          expression.lookLeft(500, myRobot);
//	          Robokind.sleep(200);
            
              

            
            
//  		  public final static int NECK_PITCH = 202;
//            public final static int NECK_YAW = 200;
//            /**
//             * Neck Roll.
//             */
//            public final static int NECK_ROLL = 201;
//			  public final static int BROWS = 300;
//  		  public final static int EYELIDS = 301;
            
//		    public final static int EYES_PITCH = 310;
//		    /**
//		     * Eye Left.
//		     */
//		    public final static int LEFT_EYE_YAW = 311;
//		    /**
//		     * Eye Right.
//		     */
//		    public final static int RIGHT_EYE_YAW = 312;
		    
//            expression.frown(400, myRobot);
//            Robokind.sleep(200);           
//            expression.smile(200, myRobot);
//            Robokind.sleep(200);
//            expression.closeEyes(200, myRobot);
//            Robokind.sleep(300);
//            expression.shakeHead(300, myRobot);
//            Robokind.sleep(200);
//            expression.openEyes(200, myRobot);
//            Robokind.sleep(300);                

//            expression.nod(400, myRobot);
//            Robokind.sleep(200); 
        	
            ///////////////////////////////////////////
            /// DISCONNECT AND EXIT
            ///////////////////////////////////////////
            
            Robokind.disconnect();
            System.exit(0);
        }
    }  
}