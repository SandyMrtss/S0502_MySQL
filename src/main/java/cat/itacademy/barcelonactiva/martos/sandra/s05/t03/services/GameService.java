package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;

import java.util.List;

public interface GameService {
    public GameDTO addGame(PlayerEntity playerEntity);
    public List<GameDTO> getAllGames(PlayerEntity playerEntity);
    public void deleteAllGames(PlayerEntity playerEntity);
}
