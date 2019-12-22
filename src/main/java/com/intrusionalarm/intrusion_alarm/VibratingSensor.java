package com.intrusionalarm.intrusion_alarm;

import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class VibratingSensor extends ConcurrentCoapResource {
    public static int VibrationCounter = 0;

    public VibratingSensor(String name) {
        super(name);

        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();
        Timer timer = new Timer();
        timer.schedule(new VibratingSensor.ContinuousTask(), 0, 1000);
    }

    private class ContinuousTask extends TimerTask {
        @Override
        public void run() {
            Random rand = new Random();
            VibrationCounter = rand.nextInt(50);
            VibrationCounter += 1;

            changed();
        }
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond("data from vibration " + VibrationCounter);

    }
}
