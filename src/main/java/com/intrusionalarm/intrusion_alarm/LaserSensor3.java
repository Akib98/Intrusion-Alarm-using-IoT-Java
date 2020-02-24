package com.intrusionalarm.intrusion_alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class LaserSensor3 extends ConcurrentCoapResource {
    public static SensorStates sensorStates;
    public static boolean Sesnor3Data;

    private class ContinuousTask extends TimerTask {
        @Override
        public void run() {
            int randomValue = ThreadLocalRandom.current().nextInt(0, 50 + 1);
            if(randomValue % 7 == 0)
            {
                Sesnor3Data = false;
            }
            else
            {
                Sesnor3Data = true;
            }
            sensorStates.setLaserSensor3data(Sesnor3Data);
            changed();
        }
    }

    public LaserSensor3(String name) {
        super(name);
        sensorStates = new SensorStates();

        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();
        Timer timer = new Timer();
        timer.schedule(new ContinuousTask(), 0, 15000);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sensorStates);
            JSONObject json = new JSONObject(jsonString);
            exchange.respond(CoAP.ResponseCode.CONTENT,json.toString(), MediaTypeRegistry.APPLICATION_JSON);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
