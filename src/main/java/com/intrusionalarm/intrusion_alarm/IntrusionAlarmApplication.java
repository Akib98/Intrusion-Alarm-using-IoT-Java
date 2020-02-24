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
    public  static String GlobalHeadCount;

    public static void main(String[] args) {

        SpringApplication.run(IntrusionAlarmApplication.class, args);

        responseData = new ResponseData();

        CoapServer coapServerforHeadCountSensor = new CoapServer(5685);
        coapServerforHeadCountSensor.add(new HeadCountSensor("headCount"));
        coapServerforHeadCountSensor.start();

        CoapClient headCountClient = new CoapClient("coap://localhost:5685/headCount");
        CoapObserveRelation headCountRelation = headCountClient.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coapHeadCountResponse) {
                String jsonString = coapHeadCountResponse.getResponseText();
                System.out.println("Response from the HeadCount sensor: " + jsonString);
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class);

                    responseData.setHeadCountInHome(sensorStates.getHeadCountInHome());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {

            }
        });

        CoapServer coapServerforLaserSensor1 = new CoapServer(5689);
        coapServerforLaserSensor1.add(new LaserSensor("laser1"));
        coapServerforLaserSensor1.start();

        CoapClient laserClient1 = new CoapClient("coap://localhost:5689/laser1");
        CoapObserveRelation relation1 = laserClient1.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coapLaser1Response) {
                String jsonString = coapLaser1Response.getResponseText();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class);

                    responseData.setLaserSensor1data(sensorStates.isLaserSensor1data());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
            }
        });

        CoapServer coapServerforLaserSensor2 = new CoapServer(5683);
        coapServerforLaserSensor2.add(new LaserSensor2("laser2"));
        coapServerforLaserSensor2.start();

        CoapClient laserClient2 = new CoapClient("coap://localhost:5683/laser2");
        CoapObserveRelation relation2 = laserClient2.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coap2LaserResponse) {
                String jsonString = coap2LaserResponse.getResponseText();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class);

                    responseData.setLaserSensor2data(sensorStates.isLaserSensor2data());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
            }
        });

        CoapServer coapServerforLaserSensor3 = new CoapServer(5684);
        coapServerforLaserSensor3.add(new LaserSensor3("laser3"));
        coapServerforLaserSensor3.start();

        CoapClient laserClient3 = new CoapClient("coap://localhost:5684/laser3");
        CoapObserveRelation relation3 = laserClient3.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coap3LaserResponse) {
                String jsonString = coap3LaserResponse.getResponseText();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class);

                    responseData.setLaserSensor3data(sensorStates.isLaserSensor3data());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
            }
        });

        CoapServer coapServerforVibrationSensor = new CoapServer(5687);
        coapServerforVibrationSensor.add(new VibratingSensor("vibration"));
        coapServerforVibrationSensor.start();

        CoapClient vibrationClient = new CoapClient("coap://localhost:5687/vibration");
        CoapObserveRelation vibrationRelation = vibrationClient.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coapVibrationResponse) {
                String jsonString = coapVibrationResponse.getResponseText();

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class);

                    responseData.setVibrationCount(sensorStates.getVibrationData());
                } catch (Exception e) {

                }

            }

            @Override
            public void onError() {

            }
        });
    }

}
