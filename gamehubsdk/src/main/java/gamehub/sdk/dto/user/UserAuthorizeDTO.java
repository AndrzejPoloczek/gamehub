package gamehub.sdk.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(description = "User authorize")
public class UserAuthorizeDTO {

    @ApiModelProperty(required = true, name = "Username", notes = "User's username")
    @NotBlank
    @Size(min = 5, max = 16)
    private String username;

    @ApiModelProperty(required = true, name = "Password", notes = "Users' password")
    @NotBlank
    @Size(min = 5, max = 16)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
