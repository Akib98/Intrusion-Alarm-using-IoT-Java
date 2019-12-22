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

        CoapServer coapServerforLaserSensor = new CoapServer(8085);
        coapServerforLaserSensor.add(new LaserSensor("laser"));
        coapServerforLaserSensor.start();

        CoapServer coapServerforVibrationSensor = new CoapServer(8086);
        coapServerforVibrationSensor.add(new VibratingSensor("vibration"));
        coapServerforVibrationSensor.start();

        CoapClient laserClient = new CoapClient("coap://localhost:8085/laser");
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

        CoapClient vibrationClient = new CoapClient("coap://localhost:8086/vibration");
        CoapObserveRelation vibrationRelation = vibrationClient.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coapVibrationResponse) {

                try {

                }
                catch (Exception e){

                }
                responseData.setVibrationCount(coapVibrationResponse.getResponseText());
                System.out.println("data from vibration = " + coapVibrationResponse.getResponseText());
            }

            @Override
            public void onError() {

            }
        });
    }

}
