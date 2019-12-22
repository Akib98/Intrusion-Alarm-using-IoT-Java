package com.intrusionalarm.intrusion_alarm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("SmartHomeSystem/")
public class IntrusionAlarmController {

    @GetMapping("home")
    public String home(){
        return "home";
    }

    @GetMapping("get-data")
    @ResponseBody
    public ResponseData getData() {
        return IntrusionAlarmApplication.responseData;
    }

    @GetMapping("get-vibration-data")
    @ResponseBody
    public ResponseData getVibrationData() {
        return IntrusionAlarmApplication.responseData;
    }
}
