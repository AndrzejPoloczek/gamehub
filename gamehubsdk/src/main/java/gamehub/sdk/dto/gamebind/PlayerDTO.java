package gamehub.sdk.dto.gamebind;

import javax.validation.constraints.NotBlank;

public class PlayerDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String displayName;


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
}
