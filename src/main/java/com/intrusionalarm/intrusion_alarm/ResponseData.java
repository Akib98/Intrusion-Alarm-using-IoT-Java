package com.intrusionalarm.intrusion_alarm;

public class ResponseData {
    public String getHeadCount() {
        return headCount;
    }

    public void setHeadCount(String headCount) {
        this.headCount = headCount;
    }

    private String headCount;

    public String getVibrationCount() {
        return vibrationCount;
    }

    public void setVibrationCount(String vibrationCount) {
        this.vibrationCount = vibrationCount;
    }

    public String vibrationCount;
}
