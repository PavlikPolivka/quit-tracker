package com.ppolivka.quittracker.controller;

import com.ppolivka.quittracker.model.User;
import com.ppolivka.quittracker.util.UserUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class HomeController {

    private final Logger logger = Logger.getLogger(HomeController.class);

    private final UserUtil userUtil;

    @Autowired
    public HomeController(UserUtil userUtil) {
        this.userUtil = userUtil;
    }

    @RequestMapping(value = "/")
    public String sendMessage(Model model) {
        Optional<User> user =  userUtil.getUser();
        user.ifPresent(user1 -> model.addAttribute("user", user1));
        return "index";
    }

}
