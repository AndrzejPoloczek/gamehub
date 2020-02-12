package gamehub.api.controller;

import gamehub.api.session.SessionUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractController {

    @Autowired
    private SessionUser sessionUser;

    protected SessionUser getSessionUser() {
        return sessionUser;
    }

    protected void validateSessionUser() {
        if (StringUtils.isBlank(sessionUser.getUsername())) {
            throw new IllegalStateException("Session user is no valid, You need to login again.");
        }
    }
}
