package com.github.rosjava.my_pub_sub_tutorial.Robokind;

import org.robokind.api.common.position.NormalizedDouble;
import org.robokind.api.motion.Joint;
import org.robokind.api.motion.messaging.RemoteRobot;
import org.robokind.api.speech.messaging.RemoteSpeechServiceClient;

import static org.robokind.api.motion.Robot.*;
import org.robokind.client.basic.Robokind;
import static org.robokind.client.basic.RobotJoints.*;

/**
 * Letters.java
 */
public class Letters {


    /**
     * Default constructor
     */
    public Letters() {
        
    }
    
    /**
     * Method to describe a letter
     * @param letter robot needs to describe
     * @param timeFrame the amount of time between speech (milliseconds)
     * @param mySpeaker the connected RemoteSpeechServiceClient
     * @param myRobot the connected RemoteRobot
     * @throws InterruptedException 
     */
    public void describeLetter(String letter, int timeFrame, RemoteSpeechServiceClient mySpeaker, RemoteRobot myRobot) throws InterruptedException {

	    	switch (letter) {
				case "i" :
					mySpeaker.speak("Put the red square in front of you");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put the yellow square vertically after the red square with three-four centimeter distance from it");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put the yellow rectangle vertically and in continuation of the yellow square");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put another yellow rectangle on top of the yellow rectangle");
					Robokind.sleep(timeFrame);
					break;
				case "E" :
					mySpeaker.speak("Put the blue rectangle horizontally in front of you");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put the yellow rectangle vertically after the blue rectangle so that to form a 90 degree from the left edge of the blue rectangle");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put the red rectangle vertically in continuation of the yellow rectangle");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put the yellow rectangle horizontally in the right edge of the red rectangle and in parallel with the blue rectangle");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put the green rectangle horizontally in the edge of red and yellow horizontal rectangles and in parrarle with blue and and yellow horizontal retangles");
					Robokind.sleep(timeFrame);
					break;
				case "T" :
					mySpeaker.speak("Put the red rectangle horizontally in front of you");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put another red rectangle horizontally and next to the first rectangle");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put the yellow rectangle vertically placed in the middle of the read rectangles pointed downwards with its long edge");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("add the green rectangle vertically in the continuation of the yellow rectangle");
					Robokind.sleep(timeFrame);
					break;
				case "C" : 
					mySpeaker.speak("Put the blue rectangle horizontally in front of you");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put the red rectangle vertically below the blue rectangle's left edge");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put the yellow rectangle vertically in continuation of the yellow rectangle");
					Robokind.sleep(timeFrame);
					mySpeaker.speak("Put the yellow rectangle horizontally in the right edge of the yellow rectangle and in parallel with the blue rectangle");
					Robokind.sleep(timeFrame);
					break;
			} 
  
    }
}
