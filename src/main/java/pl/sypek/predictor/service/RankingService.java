package pl.sypek.predictor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.sypek.predictor.counter.BasicPointCounter;
import pl.sypek.predictor.model.Match;
import pl.sypek.predictor.model.Player;
import pl.sypek.predictor.model.Prediction;
import pl.sypek.predictor.repository.PlayerRepository;
import pl.sypek.predictor.repository.PredictionRepository;
import pl.sypek.predictor.util.OutcomePointCounter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RankingService {
    final PlayerRepository playerRepository;
    final PredictionRepository predictionRepository;

    Logger logger = LoggerFactory.getLogger(RankingService.class);

    public RankingService(PlayerRepository playerRepository, PredictionRepository predictionRepository) {
        this.playerRepository = playerRepository;
        this.predictionRepository = predictionRepository;
    }

    public List<Player> getRanking() {
        return playerRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Player::getPoints).reversed())
                .collect(Collectors.toList());
    }

    public void updateRanking(Match match) {
        BasicPointCounter pointCounter = new BasicPointCounter();
        List<Prediction> predictionList = predictionRepository.findPredictionByMatchId(String.valueOf(match.getId()));
        logger.debug("predictionList: {}", predictionList);
        for (Prediction prediction : predictionList) {
            int points = OutcomePointCounter.countNewPoints(pointCounter, match, prediction);
            Optional<Player> optionalPlayer = playerRepository.findById(Long.parseLong(prediction.getUserId()));
            if (optionalPlayer.isPresent()) {
                Player player = optionalPlayer.get();
                player.setPoints(player.getPoints() + points);
                logger.debug("Player set: {}", player);
                playerRepository.save(player);
            }
        }
        logger.debug("Match {} updated", match);
    }
}
