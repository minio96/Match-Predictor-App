package pl.sypek.predictor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "player")
public class Player {

    public Player() {
    }

    public Player(Long player_id, String name, Integer points, String groupId) {
        this.player_id = player_id;
        this.name = name;
        this.points = points;
        this.groupId = groupId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long player_id;
    private String name;
    private Integer points;
    private String groupId;
}


