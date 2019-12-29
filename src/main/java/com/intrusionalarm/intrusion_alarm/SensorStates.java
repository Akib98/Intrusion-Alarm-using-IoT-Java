package com.intrusionalarm.intrusion_alarm;

public class SensorStates {

    public String getVibrationData() {
        return vibrationData;
    }

    public void setVibrationData(String vibrationData) {
        this.vibrationData = vibrationData;
    }

    String vibrationData;

    public String getHeadCountInHome() {
        return headCountInHome;
    }

    public void setHeadCountInHome(String headCountInHome) {
        this.headCountInHome = headCountInHome;
    }

    String headCountInHome;

    public boolean isLaserSensor1data() {
        return LaserSensor1data;
    }

    public void setLaserSensor1data(boolean laserSensor1data) {
        LaserSensor1data = laserSensor1data;
    }

    boolean LaserSensor1data;

    public boolean isLaserSensor2data() {
        return LaserSensor2data;
    }

    public void setLaserSensor2data(boolean laserSensor2data) {
        LaserSensor2data = laserSensor2data;
    }

    boolean LaserSensor2data;

    public boolean isLaserSensor3data() {
        return LaserSensor3data;
    }

    public void setLaserSensor3data(boolean laserSensor3data) {
        LaserSensor3data = laserSensor3data;
    }

    boolean LaserSensor3data;

}

