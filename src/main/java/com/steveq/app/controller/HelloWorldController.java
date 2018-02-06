package com.steveq.app.controller;

import com.steveq.app.controller.model.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloWorldController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(value = "/greeting")
    public Greeting sayHello(@RequestParam(value = "name", defaultValue = "") String name){
        return new Greeting(counter.getAndIncrement(),
                            String.format(template, name));
    }
}
