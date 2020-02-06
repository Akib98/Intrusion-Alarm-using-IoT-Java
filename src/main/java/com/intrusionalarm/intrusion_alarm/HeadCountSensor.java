package com.intrusionalarm.intrusion_alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;
import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class HeadCountSensor extends ConcurrentCoapResource {
    public static int HeadCountInHome = 0;
    public static SensorStates sensorStates;

    private class ContinuousTask extends TimerTask {
        @Override
        public void run() {
            HeadCountInHome = ThreadLocalRandom.current().nextInt(0, 2 + 1);
            String data = HeadCountInHome + "";
            sensorStates.setHeadCountInHome(data);
            changed();
        }
    }

    public HeadCountSensor(String name) {
        super(name);
        sensorStates = new SensorStates();
        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();
        Timer timer = new Timer();
        timer.schedule(new HeadCountSensor.ContinuousTask(), 0, 20000);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sensorStates);
            JSONObject json = new JSONObject(jsonString);
            exchange.respond(CoAP.ResponseCode.CONTENT,json.toString(), MediaTypeRegistry.APPLICATION_JSON);
        }
        catch (Exception e){

        }
    }

}
