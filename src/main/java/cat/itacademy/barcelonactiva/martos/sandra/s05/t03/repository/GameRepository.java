package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.repository;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Integer> {
    public List<GameEntity> findByPlayerEntity(PlayerEntity playerEntity);

    public void deleteByPlayerEntity(PlayerEntity playerEntity);

    @Query(value = "SELECT (SELECT COUNT(gameId)*100 FROM GameEntity WHERE playerEntity = ?1 AND dice1+dice2 = 7)/COUNT(gameId) from GameEntity WHERE playerEntity = ?1")
    public Double calcAverageByPlayerEntity(PlayerEntity playerEntity);
}