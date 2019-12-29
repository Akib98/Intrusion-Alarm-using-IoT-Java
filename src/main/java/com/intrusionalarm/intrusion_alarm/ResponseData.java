package com.intrusionalarm.intrusion_alarm;

public class ResponseData {

    public String getVibrationCount() {
        return vibrationCount;
    }

    public void setVibrationCount(String vibrationCount) {
        this.vibrationCount = vibrationCount;
    }

    private String vibrationCount;

    public String getHeadCountInHome() {
        return headCountInHome;
    }

    public void setHeadCountInHome(String headCountInHome) {
        this.headCountInHome = headCountInHome;
    }

    private  String headCountInHome;

    public boolean isLaserSensor1data() {
        return laserSensor1data;
    }

    public void setLaserSensor1data(boolean laserSensor1data) {
        this.laserSensor1data = laserSensor1data;
    }

    private boolean laserSensor1data;

    public boolean isLaserSensor2data() {
        return laserSensor2data;
    }

    public void setLaserSensor2data(boolean laserSensor2data) {
        this.laserSensor2data = laserSensor2data;
    }

    private  boolean laserSensor2data;

    public boolean isLaserSensor3data() {
        return laserSensor3data;
    }

    public void setLaserSensor3data(boolean laserSensor3data) {
        this.laserSensor3data = laserSensor3data;
    }

    private boolean laserSensor3data;

}
