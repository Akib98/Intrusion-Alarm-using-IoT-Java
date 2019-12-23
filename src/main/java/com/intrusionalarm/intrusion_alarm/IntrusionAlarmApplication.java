package com.intrusionalarm.intrusion_alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.californium.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntrusionAlarmApplication {

    @Autowired
    public static ResponseData responseData;

    public static void main(String[] args) {


        SpringApplication.run(IntrusionAlarmApplication.class, args);
        System.out.println("hello!");

        responseData = new ResponseData();

        CoapServer coapServerforLaserSensor = new CoapServer(8083);
        coapServerforLaserSensor.add(new LaserSensor("laser"));
        coapServerforLaserSensor.start();

        CoapServer coapServerforVibrationSensor = new CoapServer(8087);
        coapServerforVibrationSensor.add(new VibratingSensor("vibration"));
        coapServerforVibrationSensor.start();

        CoapServer coapServerforHeadCountSensor = new CoapServer(8085);
        coapServerforHeadCountSensor.add(new HeadCountSensor("headCount"));
        coapServerforHeadCountSensor.start();

        CoapClient laserClient = new CoapClient("coap://localhost:8083/laser");
        CoapObserveRelation relation = laserClient.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coapLaserResponse) {
                String jsonString = coapLaserResponse.getResponseText();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class );

                    responseData.setHeadCount(sensorStates.getHeadCount());
                    System.out.println("data from laser = " + sensorStates.getHeadCount());
                }
                catch (Exception e)
                {
                }
            }
            @Override
            public void onError() {
            }
        });

        CoapClient vibrationClient = new CoapClient("coap://localhost:8087/vibration");
        CoapObserveRelation vibrationRelation = vibrationClient.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coapVibrationResponse) {
                String jsonString = coapVibrationResponse.getResponseText();

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class);

                    responseData.setVibrationCount(sensorStates.getVibrationData());
                    System.out.println("data from vibration = " + jsonString);
                }
                catch (Exception e){

                }

            }

            @Override
            public void onError() {

            }
        });

        CoapClient headCountClient = new CoapClient("coap://localhost:8085/headCount");
        CoapObserveRelation headCountRelation = headCountClient.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coapHeadCountResponse) {
                String jsonString = coapHeadCountResponse.getResponseText();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class);

                    responseData.setHeadCountInHome(sensorStates.getHeadCountInHome());
                    System.out.println("data from getCountInHome = " + jsonString);

                }
                catch (Exception e){

                }
            }

            @Override
            public void onError() {

            }
        });
    }

}
