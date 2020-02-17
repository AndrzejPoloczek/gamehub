package gamehub.api.config.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GHAuthenticationEntryPointTest {

    @InjectMocks
    private GHAuthenticationEntryPoint testObj;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private AuthenticationException e;
    @Mock
    private ServletOutputStream out;

    @Test
    public void shouldResponseWithUnauthorizedError() throws Exception{

        // given
        when(response.getOutputStream()).thenReturn(out);

        // when
        testObj.commence(request, response, e);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}