package com.intrusionalarm.intrusion_alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class LaserSensor2 extends ConcurrentCoapResource {
    public static SensorStates sensorStates;
    public static boolean Sesnor2Data;

    private class ContinuousTask extends TimerTask {
        @Override
        public void run() {
            int randomValue = ThreadLocalRandom.current().nextInt(0, 50 + 1);
            if(randomValue % 6 == 0)
            { Sesnor2Data = false;}
            else
            {
                Sesnor2Data = true;
            }
            sensorStates.setLaserSensor2data(Sesnor2Data);
            changed();
        }
    }

    public LaserSensor2(String name) {
        super(name);
        sensorStates = new SensorStates();

        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();
        Timer timer = new Timer();
        timer.schedule(new ContinuousTask(), 0, 10000);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(sensorStates);
            exchange.respond(jsonString);
        }
        catch (Exception e)
        {
        }
    }
}
