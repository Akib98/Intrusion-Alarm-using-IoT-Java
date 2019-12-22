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

        CoapServer coapServer = new CoapServer(8085);
        coapServer.add(new LaserSensor("laser"));
        coapServer.start();

        CoapServer coapServer2 = new CoapServer(8086);
        coapServer2.add(new VibratingSensor("vibration"));
        coapServer2.start();

        CoapClient coapClient = new CoapClient("coap://localhost:8085/laser");
        CoapObserveRelation relation = coapClient.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coapResponse) {
                String jsonString = coapResponse.getResponseText();
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
            public void onLoad(CoapResponse coapResponse) {
                responseData.setVibrationCount(coapResponse.getResponseText());
                System.out.println("data from vibration = " + coapResponse.getResponseText());
            }

            @Override
            public void onError() {

            }
        });
    }

}
