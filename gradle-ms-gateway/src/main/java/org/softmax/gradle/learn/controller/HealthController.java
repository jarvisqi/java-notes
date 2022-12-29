package org.softmax.gradle.learn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jarvis
 */
@RestController
public class HealthController {

    @RequestMapping(path = "/health")
    public String health() {
        return "OK";
    }
}
