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

        CoapServer coapServerforLaserSensor1 = new CoapServer(8089);
        coapServerforLaserSensor1.add(new LaserSensor("laser1"));
        coapServerforLaserSensor1.start();

        CoapClient laserClient1 = new CoapClient("coap://localhost:8089/laser1");
        CoapObserveRelation relation1 = laserClient1.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coapLaser1Response) {
                String jsonString = coapLaser1Response.getResponseText();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class );

                    responseData.setLaserSensor1data(sensorStates.isLaserSensor1data());
                    //System.out.println("data from laser 1 = " + sensorStates.getHeadCount());
                }
                catch (Exception e)
                {
                }
            }
            @Override
            public void onError() {
            }
        });

        //if(Integer.parseInt(GlobalHeadCount) == 0)
        //{
            CoapServer coapServerforVibrationSensor = new CoapServer(8087);
            coapServerforVibrationSensor.add(new VibratingSensor("vibration"));
            coapServerforVibrationSensor.start();

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
        //}

        CoapServer coapServerforHeadCountSensor = new CoapServer(8085);
        coapServerforHeadCountSensor.add(new HeadCountSensor("headCount"));
        coapServerforHeadCountSensor.start();

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

        CoapServer coapServerforLaserSensor2 = new CoapServer(8083);
        coapServerforLaserSensor2.add(new LaserSensor2("laser2"));
        coapServerforLaserSensor2.start();

        CoapClient laserClient2 = new CoapClient("coap://localhost:8083/laser2");
        CoapObserveRelation relation2 = laserClient2.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coap2LaserResponse) {
                String jsonString = coap2LaserResponse.getResponseText();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    System.out.println("Data from laser2 = " + jsonString);
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class );

                    responseData.setLaserSensor2data(sensorStates.isLaserSensor2data());


                }
                catch (Exception e)
                {
                }
            }
            @Override
            public void onError() {
            }
        });

        CoapServer coapServerforLaserSensor3 = new CoapServer(8084);
        coapServerforLaserSensor3.add(new LaserSensor3("laser3"));
        coapServerforLaserSensor3.start();

        CoapClient laserClient3 = new CoapClient("coap://localhost:8084/laser3");
        CoapObserveRelation relation3 = laserClient3.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse coap3LaserResponse) {
                String jsonString = coap3LaserResponse.getResponseText();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    System.out.println("Data from laser3 = " + jsonString);
                    SensorStates sensorStates = objectMapper.readValue(jsonString, SensorStates.class );

                    responseData.setLaserSensor3data(sensorStates.isLaserSensor3data());


                }
                catch (Exception e)
                {
                }
            }
            @Override
            public void onError() {
            }
        });
    }

}
