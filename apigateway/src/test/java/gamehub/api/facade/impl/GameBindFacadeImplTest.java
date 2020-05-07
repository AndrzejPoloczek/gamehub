package gamehub.api.facade.impl;

import gamehub.api.clients.GameBindClient;
import gamehub.api.dto.ApiCurrentGameDTO;
import gamehub.api.dto.ApiGameCreateDTO;
import gamehub.api.facade.SessionUserFacade;
import gamehub.api.session.SessionUser;
import gamehub.sdk.dto.gamebind.GameBindCheckDTO;
import gamehub.sdk.dto.gamebind.GameBindDTO;
import gamehub.sdk.dto.gamebind.GameCreateDTO;
import gamehub.sdk.dto.gamebind.GameJoinDTO;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class GameBindFacadeImplTest {

    @InjectMocks
    private GameBindFacadeImpl testObj;

    @Mock
    private SessionUserFacade sessionUserFacade;
    @Mock
    private GameBindClient gameBindClient;

    @Mock
    private SessionUser sessionUser;
    @Mock
    private ApiGameCreateDTO apiGameCreateDTO;
    @Mock
    private GameBindDTO gameBindDTO;
    @Mock
    private GameBindCheckDTO gameBindCheckDTO;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Captor
    private ArgumentCaptor<GameCreateDTO> createDTOArgumentCaptor;
    @Captor
    private ArgumentCaptor<GameJoinDTO> joinDTOArgumentCaptor;

    @Before
    public void setUp() {
        when(sessionUser.getUsername()).thenReturn("username");
        when(sessionUser.getDisplayName()).thenReturn("display_name");
        when(sessionUserFacade.getSessionUser()).thenReturn(sessionUser);

        when(apiGameCreateDTO.getType()).thenReturn("TYPE");
        when(apiGameCreateDTO.getPlayers()).thenReturn(2);
        when(gameBindDTO.getGuid()).thenReturn("some_guid");
    }

    // CREATE

    @Test
    public void shouldNotCreateCauseBySessionUserFail() {

        // given
        doThrow(new IllegalStateException("some error"))
                .when(sessionUserFacade)
                .validateSessionUser();
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("some error");

        // when
        testObj.create(apiGameCreateDTO);
    }

    @Test
    public void shouldNotCreateCauseByOtherBindingExists() {

        // given
        when(sessionUser.getCurrentBind()).thenReturn("some_bind");
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("You already bind other game.");

        // when
        testObj.create(apiGameCreateDTO);
    }

    @Test
    public void shouldNotCreateCauseByClientException() {

        // given
        when(gameBindClient.create(any())).thenReturn(new ResponseEntity(HttpStatus.BAD_REQUEST));

        // when
        testObj.create(apiGameCreateDTO);

        // then
        verify(gameBindClient).create(createDTOArgumentCaptor.capture());
        verify(sessionUser, never()).setCurrentBind("some_guid");
        Assertions.assertThat(createDTOArgumentCaptor.getValue().getType()).isEqualTo("TYPE");
        Assertions.assertThat(createDTOArgumentCaptor.getValue().getPlayers()).isEqualTo(2);
        Assertions.assertThat(createDTOArgumentCaptor.getValue().getUsername()).isEqualTo("username");
        Assertions.assertThat(createDTOArgumentCaptor.getValue().getDisplayName()).isEqualTo("display_name");
    }

    @Test
    public void shouldCreate() {
        // given
        when(gameBindClient.create(any())).thenReturn(new ResponseEntity(gameBindDTO, HttpStatus.OK));

        // when
        testObj.create(apiGameCreateDTO);

        // then
        verify(gameBindClient).create(createDTOArgumentCaptor.capture());
        verify(sessionUser).setCurrentBind("some_guid");
        Assertions.assertThat(createDTOArgumentCaptor.getValue().getType()).isEqualTo("TYPE");
        Assertions.assertThat(createDTOArgumentCaptor.getValue().getPlayers()).isEqualTo(2);
        Assertions.assertThat(createDTOArgumentCaptor.getValue().getUsername()).isEqualTo("username");
        Assertions.assertThat(createDTOArgumentCaptor.getValue().getDisplayName()).isEqualTo("display_name");
    }


    // JOIN

    @Test
    public void shouldNotJoinCauseBySessionUserFail() {

        // given
        doThrow(new IllegalStateException("some error"))
                .when(sessionUserFacade)
                .validateSessionUser();
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("some error");

        // when
        testObj.join("some_guid");
    }

    @Test
    public void shouldNotJoinCauseByOtherBindingExists() {

        // given
        when(sessionUser.getCurrentBind()).thenReturn("some_bind");
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("You already bind other game.");

        // when
        testObj.join("some_guid");
    }

    @Test
    public void shouldNotJoinCauseByClientException() {

        // given
        when(gameBindClient.join(any())).thenReturn(new ResponseEntity(HttpStatus.BAD_REQUEST));

        // when
        testObj.join("some_guid");

        // then
        verify(gameBindClient).join(joinDTOArgumentCaptor.capture());
        verify(sessionUser, never()).setCurrentBind("some_guid");
        Assertions.assertThat(joinDTOArgumentCaptor.getValue().getGuid()).isEqualTo("some_guid");
        Assertions.assertThat(joinDTOArgumentCaptor.getValue().getUsername()).isEqualTo("username");
        Assertions.assertThat(joinDTOArgumentCaptor.getValue().getDisplayName()).isEqualTo("display_name");
    }

    @Test
    public void shouldJoin() {

        // given
        when(gameBindClient.join(any())).thenReturn(new ResponseEntity(gameBindDTO, HttpStatus.OK));

        // when
        testObj.join("some_guid");

        // then
        verify(gameBindClient).join(joinDTOArgumentCaptor.capture());
        verify(sessionUser).setCurrentBind("some_guid");
        Assertions.assertThat(joinDTOArgumentCaptor.getValue().getGuid()).isEqualTo("some_guid");
        Assertions.assertThat(joinDTOArgumentCaptor.getValue().getUsername()).isEqualTo("username");
        Assertions.assertThat(joinDTOArgumentCaptor.getValue().getDisplayName()).isEqualTo("display_name");
    }


    // FIND

    @Test
    public void shouldNotFindCauseBySessionUserFail() {

        // given
        doThrow(new IllegalStateException("some error"))
                .when(sessionUserFacade)
                .validateSessionUser();
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("some error");

        // when
        testObj.find();
    }

    @Test
    public void shouldFind() {

        // when
        testObj.find();

        // then
        verify(gameBindClient).find();
    }


    // FIND BY TYPE

    @Test
    public void shouldNotFindByTypeCauseBySessionUserFail() {

        // given
        doThrow(new IllegalStateException("some error"))
                .when(sessionUserFacade)
                .validateSessionUser();
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("some error");

        // when
        testObj.findByType("type");
    }

    @Test
    public void shouldFindByType() {

        // when
        testObj.findByType("type");

        // then
        verify(gameBindClient).findByType("type");
    }


    // UPDATE PLAYER STATUS

    @Test
    public void shouldNotUpdatePlayerStatusCauseBySessionUserFail() {

        // given
        doThrow(new IllegalStateException("some error"))
                .when(sessionUserFacade)
                .validateSessionUser();
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("some error");

        // when
        testObj.updatePlayerStatus("some_guid");
    }

    @Test
    public void shouldJustTriggerUpdatePlayerStatusException() {

        // given
        when(gameBindClient.updatePlayerStatus("some_guid", "username"))
                .thenReturn(new ResponseEntity(HttpStatus.BAD_REQUEST));

        // when
        testObj.updatePlayerStatus("some_guid");

        // then
        verify(sessionUser, never()).setCurrentBind(null);
    }

    @Test
    public void shouldJustTriggerUpdatePlayerStatus() {

        // given
        when(gameBindCheckDTO.isCanceled()).thenReturn(false);
        when(gameBindClient.updatePlayerStatus("some_guid", "username"))
                .thenReturn(new ResponseEntity(gameBindCheckDTO,HttpStatus.OK));

        // when
        testObj.updatePlayerStatus("some_guid");

        // then
        verify(sessionUser, never()).setCurrentBind(null);
    }

    @Test
    public void shouldTriggerUpdatePlayerStatusAndRemoveCurrentGameBind() {

        // given
        when(gameBindCheckDTO.isCanceled()).thenReturn(true);
        when(gameBindClient.updatePlayerStatus("some_guid", "username"))
                .thenReturn(new ResponseEntity(gameBindCheckDTO,HttpStatus.OK));

        // when
        testObj.updatePlayerStatus("some_guid");

        // then
        verify(sessionUser).setCurrentBind(null);
    }


    // CURRENT BIND

    @Test
    public void shouldNotGetCurrentBindCauseBySessionUserFail() {

        // given
        doThrow(new IllegalStateException("some error"))
                .when(sessionUserFacade)
                .validateSessionUser();
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("some error");

        // when
        testObj.getCurrentBind();
    }

    @Test
    public void shouldGetNoCurrentBind() {

        // given
        when(sessionUser.getCurrentBind()).thenReturn(null);

        // when
        ResponseEntity<ApiCurrentGameDTO> response = testObj.getCurrentBind();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().isProcessing()).isFalse();
        Assertions.assertThat(response.getBody().getGuid()).isEqualTo(null);

    }

    @Test
    public void shouldGetCurrentBind() {

        // given
        when(sessionUser.getCurrentBind()).thenReturn("some_bind");

        // when
        ResponseEntity<ApiCurrentGameDTO> response = testObj.getCurrentBind();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().isProcessing()).isTrue();
        Assertions.assertThat(response.getBody().getGuid()).isEqualTo("some_bind");

    }


    // CREATE

    @Test
    public void shouldNotCancelCauseBySessionUserFail() {

        // given
        doThrow(new IllegalStateException("some error"))
                .when(sessionUserFacade)
                .validateSessionUser();
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("some error");

        // when
        testObj.cancelBind("some_guid");
    }

    @Test
    public void shouldNotCancelCauseByNoBindingExists() {

        // given
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("You have no game to cancel");

        // when
        testObj.cancelBind("some_bind");
    }

    @Test
    public void shouldNotCancelCauseByClientException() {

        // given
        when(sessionUser.getCurrentBind()).thenReturn("some_bind");
        when(gameBindClient.cancelBind("some_bind", "username")).thenReturn(new ResponseEntity(HttpStatus.BAD_REQUEST));

        // when
        testObj.cancelBind("some_bind");

        // then
        verify(sessionUser, never()).setCurrentBind(null);
    }

    @Test
    public void shouldCancel() {

        // given
        when(sessionUser.getCurrentBind()).thenReturn("some_bind");
        when(gameBindClient.cancelBind("some_bind", "username")).thenReturn(new ResponseEntity(HttpStatus.OK));

        // when
        testObj.cancelBind("some_bind");

        // then
        verify(sessionUser).setCurrentBind(null);
    }
}