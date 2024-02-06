package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;

import java.util.List;

public interface GameService {
    GameDTO addGame(PlayerEntity playerEntity);
    List<GameDTO> getAllGames(PlayerEntity playerEntity);
    void deleteAllGames(PlayerEntity playerEntity);
    GameEntity gameDTOToEntity(PlayerEntity playerEntity, GameDTO gameDTO);
    GameDTO gameEntityToDTO(GameEntity gameEntity);
}
