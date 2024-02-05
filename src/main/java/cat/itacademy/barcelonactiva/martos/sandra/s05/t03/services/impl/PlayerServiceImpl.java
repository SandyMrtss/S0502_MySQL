package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.impl;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.exceptions.NoGamesPlayedException;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.Request.PlayerDTORequest;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.GameService;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.PlayerService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService {
    PlayerRepository playerRepository;
    GameService gameService;
    public PlayerServiceImpl(PlayerRepository playerRepository, GameService gameService){
        this.playerRepository = playerRepository;
        this.gameService = gameService;
    }
    @Override
    public void addPlayer(PlayerDTORequest playerDTORequest) {
        playerRepository.save(playerDTOToEntity(playerDTORequest));
    }

    @Override
    public void updatePlayer(Integer id, PlayerDTORequest playerDTORequest) {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(id);
        if(playerEntity.isEmpty()){
            throw new PlayerNotFoundException();
        }
        playerEntity.get().setUsername(playerDTORequest.getUsername());
        playerRepository.save(playerEntity.get());
    }

    @Override
    public PlayerEntity getPlayer(Integer id) {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(id);
        if(playerEntity.isEmpty()){
            throw new PlayerNotFoundException();
        }
        return playerEntity.get();
    }

    @Override
    public GameDTO playGame(Integer idPlayer) {
        PlayerEntity playerEntity = getPlayer(idPlayer);
        GameDTO gameDTO = gameService.addGame(playerEntity);
        updateSuccessRate(playerEntity, gameDTO);
        return gameDTO;
    }

    private void updateSuccessRate(PlayerEntity playerEntity, GameDTO gameDTO){
        Double successRate = playerEntity.getSuccessRate();
        double isWinGame;
        if(gameDTO.isWin()){
            isWinGame = 1.0d;
        }
        else {
            isWinGame = 0.0d;
        }
        if(successRate == null){
            successRate = isWinGame*100;
        }
        else{
            int gamesPlayed = gameService.getAllGames(playerEntity).size();
            int gamesWin = (int) (Math.round(successRate/100*gamesPlayed));
            successRate = (gamesWin + isWinGame)*100/(gamesPlayed);
        }
        playerEntity.setSuccessRate(successRate);
        playerRepository.save(playerEntity);
    }

    @Override
    public List<GameDTO> getAllGames(Integer idPlayer) {
        PlayerEntity playerEntity = getPlayer(idPlayer);
        return gameService.getAllGames(playerEntity);
    }

    @Override
    public void deleteAllGames(Integer idPlayer) {
        PlayerEntity playerEntity = getPlayer(idPlayer);
        gameService.deleteAllGames(playerEntity);
        playerEntity.setSuccessRate(null);
        playerRepository.save(playerEntity);
    }

    @Override
    public List<PlayerEntity> getAllPlayers(){
        return playerRepository.findAll();
    }

    @Override
    public List<PlayerDTO> getAllSuccessRate() {
        List<PlayerEntity> playerEntityList = getAllPlayers();
        List<PlayerDTO> playerDTOList = new ArrayList<>();
        playerEntityList.forEach(p-> {
            Double success;
            try{
                success = p.getSuccessRate();
            }
            catch (NullPointerException ex){
                success = null;
            }
            playerDTOList.add(new PlayerDTO(p.getUsername(), success));
        });
        return playerDTOList;
    }
    @Override
    public double getAverageSuccessRate(){
        List<PlayerDTO> playerDTOList = getAllSuccessRate();
        return playerDTOList
                .stream()
                .filter(p-> p.getSuccessRate() != null)
                .mapToDouble(PlayerDTO::getSuccessRate).average()
                .orElseThrow(NoGamesPlayedException::new);
    }

    @Override
    public PlayerDTO getWinner() {
        List<PlayerDTO> playerDTOList = getAllSuccessRate();
        return playerDTOList
                .stream()
                .filter(p -> p.getSuccessRate() != null)
                .max(Comparator.comparing(PlayerDTO::getSuccessRate))
                .orElseThrow(NoGamesPlayedException::new);
    }

    @Override
    public PlayerDTO getLoser() {
        List<PlayerDTO> playerDTOList = getAllSuccessRate();
        if(playerDTOList.stream().allMatch(p-> p.getSuccessRate() == null)){
            throw new NoGamesPlayedException();
        }
        return playerDTOList
                .stream()
                .filter(p-> p.getSuccessRate() != null)
                .min(Comparator.comparing(PlayerDTO::getSuccessRate))
                .orElseThrow(NoGamesPlayedException::new);
    }
    @Override
    public PlayerDTO playerToDTO(PlayerEntity playerEntity){
        double successRate = playerEntity.getSuccessRate();
        return new PlayerDTO(playerEntity.getUsername(), successRate);
    }

    @Override
    public PlayerEntity playerDTOToEntity(PlayerDTORequest playerDTORequest){
        return new PlayerEntity(playerDTORequest.getUsername());
    }
}
