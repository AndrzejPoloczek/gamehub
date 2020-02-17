package gamehub.api.config.security;

import gamehub.api.clients.UserManagerClient;
import gamehub.api.session.SessionUser;
import gamehub.sdk.user.model.UserAuthorizeDTO;
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

        sessionUser.setUsername(null);
        UserAuthorizeDTO userAuth = createUserAuth(auth);

        ResponseEntity<Boolean> response = userManagerClient.authorize(userAuth);
        if (BooleanUtils.isTrue(response.getBody())) {
            sessionUser.setUsername(userAuth.getUsername());
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

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
