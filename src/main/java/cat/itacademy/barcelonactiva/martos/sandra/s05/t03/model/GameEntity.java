package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;
    @ManyToOne
    private Integer playerId;
    @Column(nullable = false)
    private int dice1;
    @Column(nullable = false)
    private int dice2;

}