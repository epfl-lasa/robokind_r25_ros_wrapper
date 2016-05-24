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
import rosjava_r25_msgs.JointMsg;
import rosjava_r25_msgs.SpeechMsg;
import rosjava_r25_msgs.TimeoutMsg;
import static org.robokind.client.basic.RobotJoints.*;

public class AppNode extends AbstractNodeMain {

	private int rate;
	private double timeout_secs;
	private int timeout_ticks;
	private int ticks_since_target;
	// private long then;
	private int idle;
	private Log log;
	private String nameSpace;

	public AppNode() {
		// initialize with sensible defaults
		// this.nameSpace= nameSpace;
		rate = 50;
		timeout_ticks = 2;
	}

	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("rosjava/AppNode");
	}

	@Override
	public void onStart(final ConnectedNode connectedNode) {
		
		BasicConfigurator.configure();
		log = connectedNode.getLog();

		final Publisher<JointMsg> jointpublisher = connectedNode.newPublisher("/msgs/joints",
				rosjava_r25_msgs.JointMsg._TYPE);

		final Publisher<SpeechMsg> speechPublisher = connectedNode.newPublisher("/msgs/speech",
				rosjava_r25_msgs.SpeechMsg._TYPE);
		
		final Publisher<TimeoutMsg> timeoutPublisher = connectedNode.newPublisher("/msgs/robotsleep",
				rosjava_r25_msgs.TimeoutMsg._TYPE);

		/**
		 * all possible predefined joint values
		 * 
		 * WAIST,
		 *
		 * NECK_YAW, NECK_ROLL, NECK_PITCH, BROWS, EYELIDS, EYES_PITCH,
		 * LEFT_EYE_YAW, RIGHT_EYE_YAW, LEFT_SMILE, RIGHT_SMILE, JAW,
		 *
		 * LEFT_SHOULDER_PITCH, LEFT_SHOULDER_ROLL, LEFT_ELBOW_YAW,
		 * LEFT_ELBOW_PITCH, LEFT_WRIST_YAW, LEFT_HAND_GRASP,
		 * RIGHT_SHOULDER_PITCH, RIGHT_SHOULDER_ROLL, RIGHT_ELBOW_YAW,
		 * RIGHT_ELBOW_PITCH, RIGHT_WRIST_YAW, RIGHT_HAND_GRASP,
		 *
		 * LEFT_HIP_ROLL, LEFT_HIP_YAW, LEFT_HIP_PITCH, LEFT_KNEE_PITCH,
		 * LEFT_ANKLE_PITCH, LEFT_ANKLE_ROLL, RIGHT_HIP_ROLL, RIGHT_HIP_YAW,
		 * RIGHT_HIP_PITCH, RIGHT_KNEE_PITCH, RIGHT_ANKLE_PITCH, RIGHT_ANKLE_ROL
		 */

		// this cancellableLoop will be canceled automatically when the node
		// shuts down
		connectedNode.executeCancellableLoop(new CancellableLoop() {

			@Override
			protected void setup() {

				idle = 10; // rate
				// then = System.currentTimeMillis();
				ticks_since_target = 0;
			}

			@Override
			protected void loop() throws InterruptedException {
				// System.out.println(ticks_since_target);
				// System.out.println(timeout_ticks);
				if (ticks_since_target < timeout_ticks) {
					spinOnce();
					Thread.sleep(rate);
				}
				Thread.sleep(idle);

			}

			private void spinOnce() {
				System.out.println("Received");

				JointMsg jointMessage = jointpublisher.newMessage();
				jointMessage.setId(ticks_since_target);
				jointMessage.setJointId(BROWS);
				jointMessage.setJointPosition(1.0);
				jointMessage.setTimeFrame(1000);
				
				TimeoutMsg timeoutMessage = timeoutPublisher.newMessage();
				timeoutMessage.setId(ticks_since_target);
				timeoutMessage.setTimeFrame(1000);
				
				SpeechMsg speechMessage = speechPublisher.newMessage();
				speechMessage.setId(ticks_since_target);
				speechMessage.setSpeechVal("Hello! My name is Zeno");

				jointpublisher.publish(jointMessage);
				timeoutPublisher.publish(timeoutMessage);
				speechPublisher.publish(speechMessage);

				ticks_since_target++;
			}
		});
	}
};