package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.unittests;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.exceptions.NoGamesPlayedException;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.request.PlayerDTORequest;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.GameService;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.PlayerService;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.impl.PlayerServiceImpl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {
    @Mock
    private GameService gameService;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService = new PlayerServiceImpl(playerRepository, gameService);

    private PlayerEntity playerWithUsername;
    private PlayerEntity playerAnonymous;
    private List<PlayerEntity> playerEntityList;
    private List<GameDTO> usernameGamesDTO;

    @BeforeEach
    void setup(){
        playerWithUsername = new PlayerEntity("sandy");
        playerAnonymous = new PlayerEntity(null);

        playerEntityList = new ArrayList<>();
        playerEntityList.add(playerAnonymous);
        playerEntityList.add(playerWithUsername);

        usernameGamesDTO = new ArrayList<>();
        usernameGamesDTO.add(new GameDTO(1,3));
        usernameGamesDTO.add(new GameDTO(1,6));
        usernameGamesDTO.add(new GameDTO(4,2));
        usernameGamesDTO.add(new GameDTO(2,2));
        playerWithUsername.setSuccessRate(25.0);

    }

    @Test
    @DisplayName("Adds player correctly")
    void testAddsPlayer(){
        PlayerDTORequest playerDTORequest = new PlayerDTORequest("maya");

        playerEntityList.add(new PlayerEntity("maya"));
        Mockito.when(playerRepository.findAll()).thenReturn(playerEntityList);
        playerService.addPlayer(playerDTORequest);

        List<PlayerEntity> actual = playerService.getAllPlayers();
        assertTrue(new ReflectionEquals(playerEntityList).matches(actual));
    }

    @Test
    @DisplayName("Updates player correctly")
    void testUpdatePlayer(){
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(playerAnonymous));
        PlayerDTORequest playerDTORequest = new PlayerDTORequest("maya");
        playerService.updatePlayer(1, playerDTORequest);
        assertEquals("maya", playerService.getPlayer(1).getUsername());
    }

    @Test
    @DisplayName("Shows anonymous player correctly")
    void testAnonymousPlayer(){
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(playerAnonymous));
        PlayerDTO playerDTO = playerService.playerToDTO(playerService.getPlayer(1));
        assertEquals("ANONYMOUS", playerDTO.getUsername());
    }

    @Test
    @DisplayName("Gets player correctly")
    void testGetPlayer(){
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(playerWithUsername));

        PlayerEntity actual = playerService.getPlayer(1);

        assertTrue(new ReflectionEquals(playerWithUsername).matches(actual));
    }

    @Test
    @DisplayName("Throws exception when player doesn't exist")
    void testGetPlayerException(){
        assertThrows(PlayerNotFoundException.class,  () -> {
            playerService.getPlayer(50);
        });
    }

    @Test
    @DisplayName("Gets anonymous correctly")
    void testGetAnonPlayer(){
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(playerAnonymous));

        PlayerEntity actual = playerService.getPlayer(1);

        assertTrue(new ReflectionEquals(playerAnonymous).matches(actual));
    }

    @Test
    @DisplayName("Adds games correctly")
    void testAddGame(){
        GameDTO newGameDTO = new GameDTO(5,6);
        usernameGamesDTO.add(newGameDTO);

        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(playerWithUsername));
        Mockito.when(gameService.addGame(playerWithUsername)).thenReturn(newGameDTO);

        GameDTO actual =  playerService.playGame(1);

        assertTrue(new ReflectionEquals(newGameDTO).matches(actual));
    }

    @Test
    @DisplayName("Updates success rate correctly (first game)")
    void testUpdatesSuccessFirst(){
        GameDTO newGameDTO = new GameDTO(1,6);

        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(playerAnonymous));
        Mockito.when(gameService.addGame(playerAnonymous)).thenReturn(newGameDTO);

        playerService.playGame(1);

        assertEquals(100.0, playerAnonymous.getSuccessRate());
    }
    @Test
    @DisplayName("Updates success rate correctly (following games)")
    void testUpdatesSuccess(){
        GameDTO newGameDTO = new GameDTO(5,6);
        usernameGamesDTO.add(newGameDTO);

        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(playerWithUsername));
        Mockito.when(gameService.addGame(playerWithUsername)).thenReturn(newGameDTO);
        Mockito.when(gameService.getAllGames(playerWithUsername)).thenReturn(usernameGamesDTO);

        playerService.playGame(1);

        assertEquals(20.0, playerWithUsername.getSuccessRate());
    }
    @Test
    @DisplayName("Gets all games correctly")
    void testGetsAllGames(){
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(playerWithUsername));
        Mockito.when(gameService.getAllGames(playerWithUsername)).thenReturn(usernameGamesDTO);

        List<GameDTO> actual = playerService.getAllGames(1);

        assertTrue(new ReflectionEquals(usernameGamesDTO).matches(actual));
    }

    @Test
    @DisplayName("Deletes all games correctly")
    void testDeleteAllGames(){
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(playerWithUsername));

        playerService.deleteAllGames(1);

        Mockito.verify(gameService).deleteAllGames(playerWithUsername);
    }

    @Test
    @DisplayName("Gets all players correctly")
    void testGetAllPlayer(){
        List<PlayerEntity> playerEntities = new ArrayList<>();
        playerEntities.add(playerWithUsername);
        playerEntities.add(playerAnonymous);

        Mockito.when(playerRepository.findAll()).thenReturn(playerEntities);

        List<PlayerEntity> actual = playerService.getAllPlayers();

        assertTrue(new ReflectionEquals(playerEntities).matches(actual));
    }

    @Test
    @DisplayName("Gets all success rates' correctly")
    void testGetAllSuccessRate(){
        List<PlayerDTO> playerDTOList = new ArrayList<>();
        playerDTOList.add(new PlayerDTO(playerWithUsername.getUsername(), playerWithUsername.getSuccessRate()));
        playerDTOList.add(new PlayerDTO(playerAnonymous.getUsername(), playerAnonymous.getSuccessRate()));

        Mockito.when(playerRepository.findAll()).thenReturn(playerEntityList);

        List<PlayerDTO> actual = playerService.getAllSuccessRate();

        assertTrue(new ReflectionEquals(playerDTOList).matches(actual));
    }

    @Test
    @DisplayName("Gets winner correctly")
    void testGetWinner(){
        PlayerEntity maya = new PlayerEntity("maya");
        PlayerEntity javi = new PlayerEntity("javi");
        playerEntityList.add(maya);
        playerEntityList.add(javi);
        maya.setSuccessRate(50.0);

        Mockito.when(playerRepository.findAll()).thenReturn(playerEntityList);

        PlayerDTO expected = playerService.playerToDTO(maya);
        PlayerDTO winner = playerService.getWinner();

        assertTrue(new ReflectionEquals(expected).matches(winner));
    }
    @Test
    @DisplayName("Throws exception when gets winner but no games played")
    void testGetWinnerException(){
        PlayerEntity maya = new PlayerEntity("maya");
        PlayerEntity javi = new PlayerEntity("javi");
        playerEntityList.remove(playerWithUsername);
        playerEntityList.add(maya);
        playerEntityList.add(javi);

        Mockito.when(playerRepository.findAll()).thenReturn(playerEntityList);

        assertThrows(NoGamesPlayedException.class, () ->{
            playerService.getWinner();
        });
    }

    @Test
    @DisplayName("Gets loser correctly")
    void testGetLoser(){
        PlayerEntity maya = new PlayerEntity("maya");
        PlayerEntity javi = new PlayerEntity("javi");
        playerEntityList.add(maya);
        playerEntityList.add(javi);
        javi.setSuccessRate(10.0);

        Mockito.when(playerRepository.findAll()).thenReturn(playerEntityList);

        PlayerDTO expected = playerService.playerToDTO(javi);
        PlayerDTO loser = playerService.getLoser();

        assertTrue(new ReflectionEquals(expected).matches(loser));
    }
    @Test
    @DisplayName("Throws exception when gets loser but no games played")
    void testGetLoserException(){
        PlayerEntity maya = new PlayerEntity("maya");
        PlayerEntity javi = new PlayerEntity("javi");
        playerEntityList.remove(playerWithUsername);
        playerEntityList.add(maya);
        playerEntityList.add(javi);

        Mockito.when(playerRepository.findAll()).thenReturn(playerEntityList);

        assertThrows(NoGamesPlayedException.class, () ->{
            playerService.getLoser();
        });
    }
}
