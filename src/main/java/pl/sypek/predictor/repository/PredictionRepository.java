package pl.sypek.predictor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sypek.predictor.model.Prediction;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findPredictionByMatchId(String matchId);
}
