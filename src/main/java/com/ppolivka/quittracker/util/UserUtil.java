package com.ppolivka.quittracker.util;

import com.ppolivka.quittracker.dto.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class UserUtil {

    public Optional<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            Optional<Object> details = Optional.of(auth2Authentication).map(OAuth2Authentication::getUserAuthentication).map(Authentication::getDetails);
            if(details.isPresent() && details.get() instanceof Map) {
                Map userDetails = (Map) details.get();
                User user = new User() {{
                    setUserId(userDetails.get("sub").toString());
                    setUserName(userDetails.get("name").toString());
                }};
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

}
