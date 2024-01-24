package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class PlayerDTO {
    private String username;

    private double successRate;

    public PlayerDTO(String username, double successRate) {
        this.username = Objects.requireNonNullElse(username, "ANONYMUS");
        this.successRate = successRate;
    }

}
