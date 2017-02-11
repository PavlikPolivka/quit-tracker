package com.ppolivka.quittracker.controller;

import com.ppolivka.quittracker.form.TrackerForm;
import com.ppolivka.quittracker.model.User;
import com.ppolivka.quittracker.util.UserUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HomeController {

    private final Logger logger = Logger.getLogger(HomeController.class);

    private final UserUtil userUtil;

    @ModelAttribute
    public TrackerForm trackerForm() {
        TrackerForm trackerForm = new TrackerForm();
        Optional<User> user =  userUtil.getUser();
        if(user.isPresent()) {
            trackerForm.setWho(user.get().getUserName());
        }
        return trackerForm;
    }

    @Autowired
    public HomeController(UserUtil userUtil) {
        this.userUtil = userUtil;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage(Model model) {
        Optional<User> user =  userUtil.getUser();
        user.ifPresent(user1 -> model.addAttribute("user", user1));
        model.addAttribute("formClass", "hidden");
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addTracker(@ModelAttribute("trackerForm") @Valid TrackerForm trackerForm, BindingResult bindingResult, Model model) {
        Optional<User> user =  userUtil.getUser();
        user.ifPresent(user1 -> model.addAttribute("user", user1));
        model.addAttribute("formClass", "show");
        return "index";
    }

}
