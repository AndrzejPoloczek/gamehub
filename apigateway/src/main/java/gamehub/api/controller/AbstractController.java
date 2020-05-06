package gamehub.api.controller;

import gamehub.api.session.SessionUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractController {

    private SessionUser sessionUser;

    protected void validateSessionUser() {
        if (StringUtils.isBlank(sessionUser.getUsername()) || StringUtils.isBlank(sessionUser.getDisplayName())) {
            throw new IllegalStateException("Session user is no valid, You need to login again.");
        }
    }

    protected void validateCurrentBindEmpty() {
        if (StringUtils.isNotBlank(sessionUser.getCurrentBind())) {
            throw new IllegalStateException("You already bind other game.");
        }
    }

    protected void validateCurrentBind() {
        if (StringUtils.isBlank(sessionUser.getCurrentBind())) {
            throw new IllegalStateException("You have no game to cancel");
        }
    }

    protected SessionUser getSessionUser() {
        return sessionUser;
    }

    @Autowired
    public void setSessionUser(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }
}
