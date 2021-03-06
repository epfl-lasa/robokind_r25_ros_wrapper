Robokind R25 ROS Wrapper
=================

ROS Wrapper publishes and subscribes messages to manipulate Robokind R25 robot from the application. Messages include information about robot position(joint) values, speaker text, robot state and sensor data. Wrapper communicates to robot using Robokind Java API.

### Pre-requisites

Supported operating system: Ubuntu (currently ros only works with Linux)

ROS: You need to install [RosJava](http://wiki.ros.org/rosjava/Tutorials/indigo/Installation).


### Installation

- Create RosJava catkin package manually or run the following command: 

    ```
    catkin_create_rosjava_pkg PACKAGE_NAME
    ``` 



- Create RosJava catkin gradle subproject by running the following command: 

    ```
    catkin_create_rosjava_project PROJECT_NAME
    ``` 

- Add project files under your subproject directory and add Robokind API dependencies in build.gradle file: 

    ```
    dependencies {
       compile 'org.robokind:org.robokind.client.basic:0.9.5'
       compile 'org.robokind:org.robokind.api.motion:0.9.5'
       compile 'org.robokind:org.robokind.api.messaging:0.9.5'
    }
    ```

- Build project's custom messages using the following commands:

    ```
    source ~/rosjava/devel/setup.bash
    genjava_message_artifacts --verbose -p std_msgs rosjava_r25_msgs
    ```

- Add newly built custom messages under build.gradle dependencies:
    ```
    compile 'org.ros.rosjava_messages:rosjava_r25_msgs:0.2.1'
    ```

- Run AppNode and BridgeNode files after launching roscore 
