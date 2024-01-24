package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer gameId;

    @ManyToOne
    private PlayerEntity playerEntity;

    @Column(nullable = false)
    private int dice1;

    @Column(nullable = false)
    private int dice2;

    @Column(nullable = false, updatable = false, insertable = false)
    @CreationTimestamp(source = SourceType.DB)
    private Timestamp playedTime;

    public GameEntity(PlayerEntity playerEntity, int dice1, int dice2) {
        this.playerEntity = playerEntity;
        this.dice1 = dice1;
        this.dice2 = dice2;
    }

}