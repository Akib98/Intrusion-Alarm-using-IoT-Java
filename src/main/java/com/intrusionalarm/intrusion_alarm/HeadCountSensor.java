package com.intrusionalarm.intrusion_alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

import java.util.Timer;
import java.util.TimerTask;

public class HeadCountSensor extends ConcurrentCoapResource {
    public static int HeadCountInHome = 0;
    public static SensorStates sensorStates;

    public HeadCountSensor(String name) {
        super(name);
        sensorStates = new SensorStates();


        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();
        Timer timer = new Timer();
        timer.schedule(new HeadCountSensor.ContinuousTask(), 0, 3000);
    }

    private class ContinuousTask extends TimerTask {
        @Override
        public void run() {

            HeadCountInHome = (int) Math.random() * 49 + 1;
            HeadCountInHome++;
            changed();
        }
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString =  objectMapper.writeValueAsString(sensorStates);
            exchange.respond(jsonString);
        }
        catch (Exception e){

        }
    }

}
