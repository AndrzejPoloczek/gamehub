package gamehub.usermanager.controller;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserManagerControllerTest {

	private static final String JSON_DISPLAY_NAME_1 = "\"displayName\": \"User Name 1\"";
	private static final String JSON_DISPLAY_NAME_2 = "\"displayName\": \"User Name 2\"";
	private static final String JSON_USERNAME_1 = "\"username\": \"username_1\"";
	private static final String JSON_USERNAME_2 = "\"username\": \"username_2\"";
	private static final String JSON_PASSWORD_1 = "\"password\": \"password\"";
	private static final String JSON_PASSWORD_REPEAT_1 = "\"passwordRepeat\": \"password\"";

	@Autowired
    private MockMvc mockMvc;


	@Before
	public void setUp() {

	}


	@Test
	public void T06_shouldCreateUser() throws Exception {
		mockMvc.perform(post("/create")
					.content(combineJson(JSON_DISPLAY_NAME_1, JSON_USERNAME_1, JSON_PASSWORD_1, JSON_PASSWORD_REPEAT_1))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.displayName", is("User Name 1")))
				.andExpect(jsonPath("$.username", is("username_1")));
	}

	@Test
	public void T07_shouldCreateUserWithDefaultDisplayName() throws Exception {
		mockMvc.perform(post("/create")
					.content(combineJson(JSON_USERNAME_2, JSON_PASSWORD_1, JSON_PASSWORD_REPEAT_1))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.displayName", is("username_2")))
				.andExpect(jsonPath("$.username", is("username_2")));
	}

	@Test
	public void T08_shouldNotUpdateCausedByNoUserFound() throws Exception {
		mockMvc.perform(post("/update/nouser")
					.content(combineJson(JSON_DISPLAY_NAME_2))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("message", is("No user with username nouser found")));
	}

	@Test
	public void T09_shouldUpdate() throws Exception {
		mockMvc.perform(post("/update/username_2")
					.content(combineJson(JSON_DISPLAY_NAME_2))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.displayName", is("User Name 2")))
				.andExpect(jsonPath("$.username", is("username_2")));
	}

	@Test
	public void T10_shouldNotChangePasswordCausedByNoUserFound() throws Exception {
		mockMvc.perform(post("/changePassword/nouser")
					.content(combineJson(JSON_PASSWORD_1, JSON_PASSWORD_REPEAT_1))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("message", is("No user with username nouser found")));
	}

	@Test
	public void T12_shouldChangePassword() throws Exception {
		mockMvc.perform(post("/changePassword/username_2")
					.content(combineJson(JSON_PASSWORD_1, JSON_PASSWORD_REPEAT_1))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	public void t13_shouldNotGetCausedByNoUserFound() throws Exception {
		mockMvc.perform(get("/get/nouser"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("message", is("No user with username nouser found")));
	}

	@Test
	public void T14_shouldGet() throws Exception {
		mockMvc.perform(get("/get/username_2"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.displayName", is("User Name 2")))
				.andExpect(jsonPath("$.username", is("username_2")));
	}

	private String combineJson(String ...fields) {
		StringBuilder sb = new StringBuilder();
		return sb
			.append("{")
			.append(String.join(",", fields))
			.append("}")
			.toString();
	}
}