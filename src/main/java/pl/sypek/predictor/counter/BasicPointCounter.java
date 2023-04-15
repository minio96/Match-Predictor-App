package pl.sypek.predictor.counter;

import pl.sypek.predictor.model.Match;
import pl.sypek.predictor.model.Prediction;
import pl.sypek.predictor.util.MatchOutcome;

public class BasicPointCounter extends PointCounter{
    @Override
    public int countPoints(Match match, Prediction prediction) {
        int result = 0;
        if (getOutcome(match.getHomeScore(), match.getAwayScore()) == getOutcome(prediction.getHomeScore(), prediction.getAwayScore())) {
            result += 3;
        }
        if (getDiff(match.getHomeScore(), match.getAwayScore()) == getDiff(prediction.getHomeScore(), prediction.getAwayScore())) {
            result += 1;
        }
        return result;
    }

    private static MatchOutcome getOutcome(String homeScore, String awayScore) {
        MatchOutcome matchOutcome;
        if (Integer.parseInt(homeScore) > Integer.parseInt(awayScore)) {
            matchOutcome = MatchOutcome.HOST_WON;
        } else if (homeScore.equals(awayScore)) {
            matchOutcome = MatchOutcome.DRAW;
        } else {
            matchOutcome = MatchOutcome.AWAY_WON;
        }
        return matchOutcome;
    }

    private static int getDiff(String homeScore, String awayScore) {
        return Math.abs(Integer.parseInt(homeScore) - Integer.parseInt(awayScore));
    }
}
