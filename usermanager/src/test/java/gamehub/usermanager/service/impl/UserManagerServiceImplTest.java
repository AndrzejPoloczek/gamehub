package gamehub.usermanager.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gamehub.usermanager.exception.UserAlreadyExistsException;
import gamehub.usermanager.exception.UserNotFoundException;
import gamehub.usermanager.model.User;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserManagerServiceImplTest {

	private static final String DISPLAYNAME = "Display Name";
	private static final String USERNAME_1 = "username_1";
	private static final String USERNAME_2 = "username_2";
	private static final String PASSWORD = "password";
	private static final String PASSWORD_ENCODED = "password_encoded";
	private static final String NEW_PASSWORD = "new_password";
	private static final String NEW_PASSWORD_ENCODED = "new_password_encoded";

	@InjectMocks
	private UserManagerServiceImpl testObj;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();


	@Before
	public void setUp() {
		when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD_ENCODED);
		when(passwordEncoder.encode(NEW_PASSWORD)).thenReturn(NEW_PASSWORD_ENCODED);
		when(passwordEncoder.matches(PASSWORD, PASSWORD_ENCODED)).thenReturn(true);
	}

	@Test
	public void shouldCreateNewUser() throws Exception {

		// when
		User user = testObj.create(DISPLAYNAME, USERNAME_1, PASSWORD);

		// then
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(user.getDisplayName()).isEqualTo(DISPLAYNAME);
		Assertions.assertThat(user.getUsername()).isEqualTo(USERNAME_1);
		Assertions.assertThat(user.getPassword()).isEqualTo(PASSWORD_ENCODED);
	}

	@Test
	public void shouldCreateNewUserWithDefaultDisplayName() throws Exception {

		// when
		User user = testObj.create(null, USERNAME_1, PASSWORD);

		// then
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(user.getDisplayName()).isEqualTo(USERNAME_1);
		Assertions.assertThat(user.getUsername()).isEqualTo(USERNAME_1);
		Assertions.assertThat(user.getPassword()).isEqualTo(PASSWORD_ENCODED);
	}

	@Test
	public void shouldCreateAnotherUser() throws Exception {

		// when
		User user1 = testObj.create(DISPLAYNAME, USERNAME_1, PASSWORD);
		User user2 = testObj.create(DISPLAYNAME, USERNAME_2, PASSWORD);

		// then
		Assertions.assertThat(user1).isNotNull();
		Assertions.assertThat(user1.getDisplayName()).isEqualTo(DISPLAYNAME);
		Assertions.assertThat(user1.getUsername()).isEqualTo(USERNAME_1);
		Assertions.assertThat(user1.getPassword()).isEqualTo(PASSWORD_ENCODED);

		Assertions.assertThat(user2).isNotNull();
		Assertions.assertThat(user2.getDisplayName()).isEqualTo(DISPLAYNAME);
		Assertions.assertThat(user2.getUsername()).isEqualTo(USERNAME_2);
		Assertions.assertThat(user2.getPassword()).isEqualTo(PASSWORD_ENCODED);
	}

	@Test
	public void shouldNotCreateAnotherUserWithSameUsername() throws Exception {

		//given
		expectedException.expect(UserAlreadyExistsException.class);
		testObj.create(DISPLAYNAME, USERNAME_1, PASSWORD);

		// when
		testObj.create("qw", USERNAME_1, "qw");
	}

	@Test
	public void shouldGetUser() throws Exception {

		//given
		testObj.create(DISPLAYNAME, USERNAME_1, PASSWORD);

		// when
		User user = testObj.get(USERNAME_1);

		// then
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(user.getDisplayName()).isEqualTo(DISPLAYNAME);
		Assertions.assertThat(user.getUsername()).isEqualTo(USERNAME_1);
		Assertions.assertThat(user.getPassword()).isEqualTo(PASSWORD_ENCODED);
	}

	@Test
	public void shouldNotGetUser() throws Exception {

		//given
		expectedException.expect(UserNotFoundException.class);

		// when
		testObj.get(USERNAME_1);
	}

	@Test
	public void shouldAuthorizeUser() throws Exception {

		//given
		testObj.create(DISPLAYNAME, USERNAME_1, PASSWORD);

		// when
		boolean authorize = testObj.authorize(USERNAME_1, PASSWORD);

		// then
		Assertions.assertThat(authorize).isTrue();
	}

	@Test
	public void shouldNotAuthorizeUser() throws Exception {

		//given
		testObj.create(DISPLAYNAME, USERNAME_1, PASSWORD);

		// when
		boolean authorize = testObj.authorize(USERNAME_2, PASSWORD);

		// then
		Assertions.assertThat(authorize).isFalse();
	}

	@Test
	public void shouldChangeUsersPassword() throws Exception {

		//given
		testObj.create(DISPLAYNAME, USERNAME_1, PASSWORD);

		// when
		testObj.changePassword(USERNAME_1, NEW_PASSWORD);

		// then
		User user = testObj.get(USERNAME_1);
		Assertions.assertThat(user.getPassword()).isEqualTo(NEW_PASSWORD_ENCODED);
	}

	@Test
	public void shouldUpdateUser() throws Exception {

		//given
		testObj.create(DISPLAYNAME, USERNAME_1, PASSWORD);

		// when
		testObj.update(USERNAME_1, "NEWNAME");

		// then
		User user = testObj.get(USERNAME_1);
		Assertions.assertThat(user.getDisplayName()).isEqualTo("NEWNAME");
	}

}
