package com.ppolivka.quittracker.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final Logger logger = Logger.getLogger(HomeController.class);

    @RequestMapping(value = "/")
    public String sendMessage() {
        return "home";
    }

}
