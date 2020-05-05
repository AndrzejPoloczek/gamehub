package gamehub.api.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionUser {

    private static final long serialVersionUID = 1L;
    private String username;
    private String displayName;
    private String currentBind;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCurrentBind() {
        return currentBind;
    }

    public void setCurrentBind(String currentBind) {
        this.currentBind = currentBind;
    }
}
