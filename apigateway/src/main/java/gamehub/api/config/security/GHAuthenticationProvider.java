package gamehub.api.config.security;

import gamehub.api.clients.UserManagerClient;
import gamehub.api.session.SessionUser;
import gamehub.sdk.dto.user.UserAuthorizeDTO;
import gamehub.sdk.dto.user.UserInfoDTO;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GHAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserManagerClient userManagerClient;

    @Autowired
    private SessionUser sessionUser;


    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        upadteSessionUser(null, null);
        UserAuthorizeDTO userAuth = createUserAuth(auth);

        ResponseEntity<Boolean> response = userManagerClient.authorize(userAuth);
        if (BooleanUtils.isTrue(response.getBody())) {
            ResponseEntity<UserInfoDTO> userInfo = userManagerClient.getByUsername(userAuth.getUsername());
            upadteSessionUser(userInfo.getBody().getUsername(), userInfo.getBody().getDisplayName());
            return new UsernamePasswordAuthenticationToken
                    (userAuth.getUsername(), userAuth.getPassword(), Collections.emptyList());
        } else {
            throw new BadCredentialsException("Authentication failed.");
        }
    }

    private UserAuthorizeDTO createUserAuth(Authentication auth) {
        UserAuthorizeDTO userAuth = new UserAuthorizeDTO();
        userAuth.setUsername(auth.getName());
        userAuth.setPassword(auth.getCredentials().toString());
        return userAuth;
    }

    private void upadteSessionUser(String username, String displayName) {
        sessionUser.setUsername(username);
        sessionUser.setDisplayName(displayName);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
