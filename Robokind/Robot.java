package com.github.rosjava.my_pub_sub_tutorial.Robokind;

import org.robokind.api.motion.Joint;
import org.robokind.api.motion.Robot.JointId;
import org.robokind.api.motion.Robot.RobotPositionHashMap;
import org.robokind.api.motion.Robot.RobotPositionMap;
import org.robokind.api.motion.messaging.RemoteRobot;
import org.robokind.api.sensor.DeviceReadPeriodEvent;
import org.robokind.api.sensor.FilteredVector3Event;
import org.robokind.api.sensor.Vector3Event;
import org.robokind.api.sensor.imu.RemoteAccelerometerServiceClient;
import org.robokind.api.sensor.imu.RemoteCompassServiceClient;
import org.robokind.api.sensor.imu.RemoteGyroscopeServiceClient;
import org.robokind.api.speech.messaging.RemoteSpeechServiceClient;
import org.robokind.client.basic.Robokind;
import org.robokind.impl.sensor.AccelerometerConfigRecord;
import org.robokind.impl.sensor.CompassConfigRecord;
import org.robokind.impl.sensor.DeviceReadPeriodRecord;
import org.robokind.impl.sensor.GyroConfigRecord;
import org.robokind.impl.sensor.HeaderRecord;


import org.jflux.api.core.Listener;
import org.robokind.api.common.position.NormalizedDouble;

public class Robot {

	private static RemoteRobot myRobot;
	private static RobotPositionMap myGoalPositions;
	private static RemoteSpeechServiceClient mySpeaker;
	private static RemoteAccelerometerServiceClient accel;
	private static RemoteGyroscopeServiceClient gyro;
	private static RemoteCompassServiceClient compass;

	public Robot(Boolean sensors) {

		String robotID = "myRobot";
		String robotIP = "192.168.2.3";

		// set addresses
		SetSettings settings = new SetSettings(robotID, robotIP);

		// make connection
		myRobot = Robokind.connectRobot();
		mySpeaker = Robokind.connectSpeechService();
		System.out.println("I am in Constructior");

		// set sensor configuration
		if (sensors) {
			setSensorConfigs();
		}

	}

	// execute joint
	public boolean setJoints(int jointId, double JointValue, int timeFrame) {
		// System.out.println(jointId);
		// System.out.println(JointValue);
		// System.out.println(timeFrame);
		if (myRobot.isConnected()) {
			myGoalPositions = new RobotPositionHashMap();
			JointId Joint_Id = new JointId(myRobot.getRobotId(), new Joint.Id(jointId));
			myGoalPositions.put(Joint_Id, new NormalizedDouble(JointValue));
			myRobot.move(myGoalPositions, timeFrame);
			return true;
		}
		return false;
	}

	// robot speech
	public boolean setSpeech(String speechValue) {
		System.out.println(speechValue);
		if (myRobot.isConnected()) {
			mySpeaker.speak(speechValue);
			return true;
		}
		return false;
	}

	// robot sleep/timeout
	public boolean setTimeOut(int timeout) {
		System.out.println(timeout);
		if (myRobot.isConnected()) {
			Robokind.sleep(timeout);
			return true;
		}
		return false;
	}
	
	// sensor configuration
	public void setSensorConfigs() {
		
		DeviceReadPeriodEvent<HeaderRecord> readPeriod = new DeviceReadPeriodRecord();
		HeaderRecord header = new HeaderRecord();

		header.setFrameId(0);
		header.setSequenceId(0);
		header.setTimestamp(0L);
		readPeriod.setHeader(header);
		readPeriod.setPeriod(100.0);

		// connect accelorometer, gyroscope and compass
		accel = Robokind.connectAccelerometer();
		gyro = Robokind.connectGyroscope();
		compass = Robokind.connectCompass();

		// set read period for them all
		readPeriod.setPeriod(1000.0);
		accel.setReadPeriod(readPeriod);
		gyro.setReadPeriod(readPeriod);
		compass.setReadPeriod(readPeriod);

		// config records for the three?
		AccelerometerConfigRecord accelConfig = new AccelerometerConfigRecord();
		GyroConfigRecord gyroConfig = new GyroConfigRecord();
		CompassConfigRecord compassConfig = new CompassConfigRecord();

		// setting headers for all three
		accelConfig.setHeader(header);
		gyroConfig.setHeader(header);
		compassConfig.setHeader(header);

		// accelorometer address and values
		accelConfig.setRegisterAddress(45);
		accelConfig.setRegisterValue(8);

		// gyro config - no idea what this does
		gyroConfig.setCtl1(15);
		gyroConfig.setCtl2(-1);
		gyroConfig.setCtl3(-1);
		gyroConfig.setCtl4(-1);
		gyroConfig.setCtl5(-1);

		// setting up compass config - no idea about this either
		compassConfig.setAverage(3);
		compassConfig.setBias(0);
		compassConfig.setGain(7);
		compassConfig.setRate(2);

		// confirm configs and send
		accel.sendConfig(accelConfig);
		gyro.sendConfig(gyroConfig);
		compass.sendConfig(compassConfig);
	}

	public static class AccelListener implements Listener<FilteredVector3Event> {
		public void handleEvent(FilteredVector3Event t) {
			Vector3Event v = t.getFilteredVector();
			Vector3Event r = t.getRawVector();
			System.out.println("Accelerometer (f): " + v.getX() + ", " + v.getY() + ", " + v.getZ());
			System.out.println("Accelerometer (r): " + r.getX() + ", " + r.getY() + ", " + r.getZ());
		}
	}

	public static class GyroListener implements Listener<FilteredVector3Event> {
		public void handleEvent(FilteredVector3Event t) {
			Vector3Event v = t.getFilteredVector();
			Vector3Event r = t.getRawVector();
			System.out.println("Gyroscope (f): " + v.getX() + ", " + v.getY() + ", " + v.getZ());
			System.out.println("Gyroscope (r): " + r.getX() + ", " + r.getY() + ", " + r.getZ());
		}
	}

	public static class CompassListener implements Listener<FilteredVector3Event> {
		public void handleEvent(FilteredVector3Event t) {
			Vector3Event v = t.getFilteredVector();
			Vector3Event r = t.getRawVector();
			System.out.println("Compass (f): " + v.getX() + ", " + v.getY() + ", " + v.getZ());
			System.out.println("Compass (r): " + r.getX() + ", " + r.getY() + ", " + r.getZ());
		}
	}
	
	// get accelorometer
	public void getAccel() {
		accel.addListener(new AccelListener());
	}
	
	// get gyroscope
	public void getGyro() {
		gyro.addListener(new GyroListener());
	}
	
	// get compass
	public void getCompass() {
		compass.addListener(new CompassListener());
	}

	public void disconnect() {
		Robokind.disconnect();
		System.exit(0);
	}

}
