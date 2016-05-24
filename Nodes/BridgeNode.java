/*
 * Copyright (C) 2016.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.rosjava.my_pub_sub_tutorial.Nodes;

import static org.robokind.client.basic.RobotJoints.BROWS;

//import static org.robokind.client.basic.RobotJoints.BROWS;

import org.apache.commons.logging.Log;
import org.apache.log4j.BasicConfigurator;
import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import com.github.rosjava.my_pub_sub_tutorial.Robokind.Robot;

import geometry_msgs.Quaternion;
import geometry_msgs.Vector3;
import rosjava_r25_msgs.JointMsg;
import rosjava_r25_msgs.SpeechMsg;
import rosjava_r25_msgs.OutputMsg;
import rosjava_r25_msgs.TimeoutMsg;
import tf2_msgs.TFMessage;

public class BridgeNode extends AbstractNodeMain {

	private int sequenceNumber = 0;
	private String nameSpace;
	private Log log;
	private Integer jointMessageId;
	private Integer jointId;
	private Integer jointTimeFrame;
	private Double jointPosition;
	private Integer speechMessageId;
	private String speechText;
	private Integer timeoutMessageId;
	private Integer robotTimeOut;
	private Robot r25;

	public BridgeNode() {
		this.speechText = null;
		this.jointPosition = 0.0;
		this.jointId = 0;
		this.jointTimeFrame = 0;
		this.robotTimeOut = 0;
	}

	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("rosjava/BridgeNode");
	}

	@Override
	public void onStart(ConnectedNode connectedNode) {
		BasicConfigurator.configure();
		log = connectedNode.getLog();
		r25 = new Robot(true);

		// joint message subscriber
		final Subscriber<JointMsg> jointSubscriber = connectedNode.newSubscriber("/msgs/joints",
				rosjava_r25_msgs.JointMsg._TYPE);
		
		// robot timeout message subscriber
		final Subscriber<TimeoutMsg> timeoutSubscriber = connectedNode.newSubscriber("/msgs/robotsleep",
				rosjava_r25_msgs.TimeoutMsg._TYPE);
		
		// speech message subscriber
		final Subscriber<SpeechMsg> speechSubscriber = connectedNode.newSubscriber("/msgs/speech",
				rosjava_r25_msgs.SpeechMsg._TYPE);
		
		// robot output publisher
		final Publisher<OutputMsg> robotOutputPublisher = connectedNode.newPublisher("/robot/output",
				rosjava_r25_msgs.OutputMsg._TYPE);

//		final Subscriber<tf2_msgs.TFMessage> attentiontrackerSubscriber = connectedNode.newSubscriber("/tf",
//				tf2_msgs.TFMessage._TYPE);
//
//		attentiontrackerSubscriber.addMessageListener(new MessageListener<tf2_msgs.TFMessage>() {
//			@Override
//			public void onNewMessage(tf2_msgs.TFMessage tfMessage) {
//
//				sequenceNumber++;
//				String FrameName = tfMessage.getTransforms().get(0).getChildFrameId().toString();
//				log.info(FrameName);
//				
//				if (FrameName == "face_0") {
//					Vector3 translation = tfMessage.getTransforms().get(0).getTransform().getTranslation();
//					Quaternion rotation = tfMessage.getTransforms().get(0).getTransform().getRotation();
//					log.info(rotation.getX());
//					log.info(rotation.getY());
//					log.info(rotation.getZ());
//				}
//
//			}
//		});
		
		// joint message listener
		jointSubscriber.addMessageListener(new MessageListener<JointMsg>() {

			@Override
			public void onNewMessage(JointMsg message) {

				executeJoints(message, robotOutputPublisher);

			}

		});
		
		// robot timeout message listener
		timeoutSubscriber.addMessageListener(new MessageListener<TimeoutMsg>() {

			@Override
			public void onNewMessage(TimeoutMsg message) {

				sleepRobot(message, robotOutputPublisher);

			}
		});
		
		// speech message listener
		speechSubscriber.addMessageListener(new MessageListener<SpeechMsg>() {

			@Override
			public void onNewMessage(SpeechMsg message) {

				executeSpeech(message, robotOutputPublisher);

			}
		});

	}
	
	// get joint values from subscriber and execute
	private void executeJoints(JointMsg message, Publisher<OutputMsg> robotOutputPublisher) {

		String output;
		log.info("Received joint message: messageId = " + message.getId() + ", jointId = " + message.getJointId()
				+ ", jointPosition = " + message.getJointPosition() + ", timeFrame = " + message.getTimeFrame());

		if (message.getId() != 0 && message.getJointId() != 0 && message.getJointPosition() <= 1
				&& message.getJointPosition() >= 0) {

			jointMessageId = message.getId();
			jointId = message.getJointId();
			jointTimeFrame = message.getTimeFrame();
			jointPosition = message.getJointPosition();

			Boolean robotExecutionResult = r25.setJoints(jointId, jointPosition, jointTimeFrame);
			output = "Execution success:" + robotExecutionResult;

			// log.info(jointId);
		} else {
			output = "Invalid joint message values";
			log.info(output);
		}
		
		// publish execution result
		OutputMsg outputMessage = robotOutputPublisher.newMessage();
		outputMessage.setType("joint");
		outputMessage.setMessageId(jointMessageId);
		outputMessage.setResult(output);
		robotOutputPublisher.publish(outputMessage);

		resetJointValues();

	}

	// get robot timeout values from subscriber and execute
	private void sleepRobot(TimeoutMsg message, Publisher<OutputMsg> robotOutputPublisher) {

		String output;
		log.info("Received timeout message: messageId = " + message.getId() + ", timeframe value = "
				+ message.getTimeFrame());

		if (message.getId() != 0 && message.getTimeFrame() != 0) {

			timeoutMessageId = message.getId();
			robotTimeOut = message.getTimeFrame();

			Boolean robotExecutionResult = r25.setTimeOut(robotTimeOut);
			output = "Execution success:" + robotExecutionResult;

			// log.info(speechMessageId);
		} else {
			output = "Invalid speech message value";
			log.info(output);
		}
		
		// publish execution result
		OutputMsg outputMessage = robotOutputPublisher.newMessage();
		outputMessage.setType("robotTimeOut");
		outputMessage.setMessageId(timeoutMessageId);
		outputMessage.setResult(output);
		robotOutputPublisher.publish(outputMessage);
		robotTimeOut = 0;

	}

	// get speech values from subscriber and execute
	private void executeSpeech(SpeechMsg message, Publisher<OutputMsg> robotOutputPublisher) {

		String output;
		log.info("Received speech message: messageId = " + message.getId() + ", speech value = "
				+ message.getSpeechVal());

		if (message.getId() != 0 && message.getSpeechVal() != null) {

			speechMessageId = message.getId();
			speechText = message.getSpeechVal();

			Boolean robotExecutionResult = r25.setSpeech(speechText);
			output = "Execution success:" + robotExecutionResult;

			// log.info(speechMessageId);
		} else {
			output = "Invalid speech message value";
			log.info(output);
		}

		// publish execution result
		OutputMsg outputMessage = robotOutputPublisher.newMessage();
		outputMessage.setType("speech");
		outputMessage.setMessageId(speechMessageId);
		outputMessage.setResult(output);
		robotOutputPublisher.publish(outputMessage);

		resetSpeechValues();

	}
	
	// reset joint values
	private void resetJointValues() {
		this.speechMessageId = 0;
		this.speechText = null;
	}
	
	// reset speech values
	private void resetSpeechValues() {
		this.jointPosition = 0.0;
		this.jointId = 0;
		this.jointTimeFrame = 0;
		this.jointMessageId = 0;
	}
	
	// disconnect from the robot
	protected void finalize() {
		r25.disconnect();
	}
}
