package com.allstate.quickclaimsserver.control;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> checkHealth() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "ok");
        map.put("date and time", LocalDateTime.now());
        return map;
    }

}
