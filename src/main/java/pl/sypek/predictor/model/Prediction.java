package pl.sypek.predictor.model;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Getter
@Setter
@Entity
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String userId;
    String matchId;
    String homeScore;
    String awayScore;
}


