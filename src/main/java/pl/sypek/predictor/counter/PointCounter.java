package pl.sypek.predictor.counter;

import pl.sypek.predictor.model.Match;
import pl.sypek.predictor.model.Prediction;

public abstract class PointCounter {
    abstract public int countPoints(Match match, Prediction prediction);
}
