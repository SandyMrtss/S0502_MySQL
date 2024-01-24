package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.impl;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.repository.GameRepository;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.GameService;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.PlayerService;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.utils.RandomDiceGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    private GameDTO newGame() {
        return new GameDTO(RandomDiceGenerator.newRandomDice(), RandomDiceGenerator.newRandomDice());
    }

    @Override
    public GameDTO addGame(PlayerEntity playerEntity){
        GameDTO gameDTO = newGame();
        gameRepository.save(gameDTOToEntity(playerEntity, gameDTO));
        return gameDTO;
    }

    @Override
    public List<GameDTO> getAllGames(PlayerEntity playerEntity) {
        List<GameEntity> gameEntityList = gameRepository.findByPlayerEntity(playerEntity);
        List<GameDTO> gameDTOList = new ArrayList<>();
        gameEntityList.stream().toList().forEach(g-> gameDTOList.add(gameEntityToDTO(g)));
        return gameDTOList;
    }

    @Override
    public void deleteAllGames(PlayerEntity playerEntity) {
        gameRepository.deleteByPlayerEntity(playerEntity);
    }

    @Override
    public double getSuccessRate(PlayerEntity playerEntity) {
        Double success = gameRepository.calcAverageByPlayerEntity(playerEntity);
        if(success == null){
            return 0.0;
        }
        return success;
    }

    private GameEntity gameDTOToEntity(PlayerEntity playerEntity, GameDTO gameDTO){
        return new GameEntity(playerEntity, gameDTO.getDice1(), gameDTO.getDice2());
    }
    private GameDTO gameEntityToDTO(GameEntity gameEntity){
        return new GameDTO(gameEntity.getDice1(), gameEntity.getDice2());
    }
}
