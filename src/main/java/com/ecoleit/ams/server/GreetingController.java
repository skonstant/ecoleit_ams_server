package com.ecoleit.ams.server;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam String name) {
        return new Greeting(1, name);
    }

}
