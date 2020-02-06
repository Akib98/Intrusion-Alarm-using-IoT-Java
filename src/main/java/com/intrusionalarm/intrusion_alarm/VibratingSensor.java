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


public class VibratingSensor extends ConcurrentCoapResource {
    public static SensorStates sensorStates;
    public static int VibrationCounter = 0;

    public VibratingSensor(String name) {
        super(name);
        sensorStates = new SensorStates();

        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();
        Timer timer = new Timer();
        timer.schedule(new VibratingSensor.ContinuousTask(), 0, 3000);
    }

    private class ContinuousTask extends TimerTask {
        @Override
        public void run() {
            Random rand = new Random();
            VibrationCounter = rand.nextInt(50);
            VibrationCounter += 1;
            String data = VibrationCounter + "";
            sensorStates.setVibrationData(data);
            changed();
        }
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //String jsonString = objectMapper.writeValueAsString(sensorStates);
            String jsonString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sensorStates);
            JSONObject json = new JSONObject(jsonString);
            exchange.respond(CoAP.ResponseCode.CONTENT,json.toString(), MediaTypeRegistry.APPLICATION_JSON);
            //exchange.respond(jsonString);
        }
        catch (Exception e)
        {

        }

    }
}
