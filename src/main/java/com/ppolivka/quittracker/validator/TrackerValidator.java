package com.ppolivka.quittracker.validator;

import com.ppolivka.quittracker.dto.TrackerForm;
import com.ppolivka.quittracker.service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class TrackerValidator implements Validator {

    private final TrackerService trackerService;

    @Autowired
    public TrackerValidator(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(TrackerForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TrackerForm tracker = (TrackerForm) target;
        if(trackerService.getTrackerByUrl(tracker.getUrl()) != null) {
            errors.rejectValue("url", "notUnique", "must be unique");
        }
        if(!tracker.getUrl().matches("[a-zA-Z0-9.-_]+")) {
            errors.rejectValue("url", "alphanumeric", "must be alpha numeric");
        }

    }
}
