package com.intrusionalarm.intrusion_alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

import java.util.Timer;
import java.util.TimerTask;

public class LaserSensor3 extends ConcurrentCoapResource {
    public static SensorStates sensorStates;

    public static boolean Sesnor3Data;


    public LaserSensor3(String name) {
        super(name);
        sensorStates = new SensorStates();

        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();
        Timer timer = new Timer();
        timer.schedule(new ContinuousTask(), 0, 5000);
    }

    private class ContinuousTask extends TimerTask {
        @Override
        public void run() {
            //if(Counter)
                //Counter = false;
            //else
                if(Sesnor3Data== true)
                { Sesnor3Data = false;}

                else if(Sesnor3Data == false)
                {Sesnor3Data = true;}
            //String data = Counter + "";
            sensorStates.setLaserSensor3data(Sesnor3Data);
            //System.out.println("Sesnor from laser3 = " + sensorStates.isLaserSensor3data());
            //System.out.println("sesnor from laser2 = " + Sesnor2Data);
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
