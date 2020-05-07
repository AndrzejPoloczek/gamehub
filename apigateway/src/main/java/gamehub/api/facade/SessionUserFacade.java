package gamehub.api.facade;

import gamehub.api.session.SessionUser;

public interface SessionUserFacade {

    /**
     * Validate if session user exists
     */
    void validateSessionUser();

    /**
     * Gets current session user
     * @return
     */
    SessionUser getSessionUser();
}
