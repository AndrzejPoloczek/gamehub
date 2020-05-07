package gamehub.api.facade.impl;

import gamehub.api.facade.SessionUserFacade;
import gamehub.api.session.SessionUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionUserFacadeImpl implements SessionUserFacade {

    private SessionUser sessionUser;

    @Override
    public void validateSessionUser() {
        if (StringUtils.isBlank(sessionUser.getUsername()) || StringUtils.isBlank(sessionUser.getDisplayName())) {
            throw new IllegalStateException("Session user is no valid, You need to login again.");
        }
    }

    @Override
    public SessionUser getSessionUser() {
        return sessionUser;
    }

    @Autowired
    public void setSessionUser(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }
}
