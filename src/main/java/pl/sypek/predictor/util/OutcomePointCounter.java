package pl.sypek.predictor.util;

import pl.sypek.predictor.counter.PointCounter;
import pl.sypek.predictor.model.Match;
import pl.sypek.predictor.model.Prediction;

public class OutcomePointCounter {
    public static int countNewPoints(PointCounter counter, Match match, Prediction prediction) {
        return counter.countPoints(match, prediction);
    }
}
