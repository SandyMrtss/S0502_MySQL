package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "players", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToMany(mappedBy = "games")
    private Integer playerId;

    private String username;

    @Column(nullable = false, updatable = false, insertable = false)
    @CreationTimestamp
    private Timestamp registerDate;
}
