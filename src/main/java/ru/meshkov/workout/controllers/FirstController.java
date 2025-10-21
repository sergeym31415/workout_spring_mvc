package ru.meshkov.workout.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstController {

    @GetMapping("/first")
    public String first() {
        return "hello_sergey";
    }

    @GetMapping("/sergey")
    public String saySergey() {
        return "sergey";
    }
}
