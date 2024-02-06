package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.repository;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Integer> {
    List<GameEntity> findByPlayerEntity(PlayerEntity playerEntity);
}