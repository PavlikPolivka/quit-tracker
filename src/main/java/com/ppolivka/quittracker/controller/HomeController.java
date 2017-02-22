package com.ppolivka.quittracker.controller;

import com.ppolivka.quittracker.dto.TrackerForm;
import com.ppolivka.quittracker.dto.User;
import com.ppolivka.quittracker.service.TrackerService;
import com.ppolivka.quittracker.util.UserUtil;
import com.ppolivka.quittracker.validator.TrackerValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HomeController {

    private final Logger logger = Logger.getLogger(HomeController.class);

    private final UserUtil userUtil;
    private final TrackerService trackerService;
    private final TrackerValidator trackerValidator;

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
    public HomeController(UserUtil userUtil, TrackerService trackerService, TrackerValidator trackerValidator) {
        this.userUtil = userUtil;
        this.trackerService = trackerService;
        this.trackerValidator = trackerValidator;
    }

    @InitBinder("trackerForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(trackerValidator);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage(Model model) {
        model.addAttribute("formClass", "hidden");
        fillModel(model);
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addTracker(@ModelAttribute("trackerForm") @Valid TrackerForm trackerForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("formClass", "show");
        } else {
            model.addAttribute("formClass", "hidden");
            trackerService.addNewTracker(trackerForm);
        }
        fillModel(model);
        return "index";
    }

    private void fillModel(Model model) {
        Optional<User> userOptional =  userUtil.getUser();
        userOptional.ifPresent(user -> model.addAttribute("user", user));
        userOptional.ifPresent(user -> model.addAttribute("myTrackers", trackerService.listMyTrackers()));
        model.addAttribute("recentTrackers", trackerService.listRecentTrackers());
    }

}
