package com.intrusionalarm.intrusion_alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

import java.util.Timer;
import java.util.TimerTask;

public class LaserSensor extends ConcurrentCoapResource {
    public static SensorStates sensorStates;

    public static boolean Sesnor1Data;


    public LaserSensor(String name) {
        super(name);
        sensorStates = new SensorStates();

        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();
        Timer timer = new Timer();
        timer.schedule(new ContinuousTask(), 0, 2000);
    }

    private class ContinuousTask extends TimerTask {
        @Override
        public void run() {
            if(Sesnor1Data== true)
            { Sesnor1Data = false;}

            else if(Sesnor1Data == false)
            {Sesnor1Data = true;}
            sensorStates.setLaserSensor1data(Sesnor1Data);
            changed();
        }
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
