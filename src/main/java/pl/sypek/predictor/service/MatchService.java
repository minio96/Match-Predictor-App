package pl.sypek.predictor.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.sypek.predictor.model.AllMatchesResponse;
import pl.sypek.predictor.model.Match;
import pl.sypek.predictor.repository.MatchRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MatchService {
    final TokenService tokenService;
    final PlayerService playerService;
    final MatchRepository matchRepository;
    final RestTemplate restTemplate;

    final RankingService rankingService;

    public static final Logger logger = LoggerFactory.getLogger(MatchService.class);

    public MatchService(TokenService tokenService, PlayerService playerService, MatchRepository matchRepository,
                        RestTemplate restTemplate, RankingService rankingService) {
        this.tokenService = tokenService;
        this.playerService = playerService;
        this.matchRepository = matchRepository;
        this.restTemplate = restTemplate;
        this.rankingService = rankingService;
    }

    @PostConstruct
    public void populateMatches() {
        if (matchRepository.findAll().isEmpty()) {
            logger.info("Getting all matches from API");
            HttpEntity<String> entity = new HttpEntity<>(getHttpHeaders());
            ResponseEntity<AllMatchesResponse> allMatchesResponseEntity = restTemplate.exchange("/match", HttpMethod.GET, entity, AllMatchesResponse.class);
            if (allMatchesResponseEntity.getStatusCode() == HttpStatus.OK && allMatchesResponseEntity.getBody().getMatchList() != null) {
                matchRepository.saveAll(allMatchesResponseEntity.getBody().getMatchList());
            }
        }
        logger.info("Matches already existing.");
    }

    @Scheduled(cron = "0 15 0,16,19,22 * * *")
    public void updateMatchByDate() throws ParseException {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        Map<String, String> date = Collections.singletonMap("date", today);
        ResponseEntity<AllMatchesResponse> matchesByDay = restTemplate.exchange("/bydate", HttpMethod.POST, new HttpEntity<>(date, getHttpHeaders()), AllMatchesResponse.class);
        updateMatchesOneDay(matchesByDay.getBody().getMatchList(), today);
    }

    private void updateMatchesOneDay(List<Match> matches, String today) throws ParseException {
        if (matches != null) {
            Date from = new SimpleDateFormat("MM/dd/yyyy").parse(today);
            Date to = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(today + " 23:59");
            List<Match> queriedMatchList = matchRepository.findByMatchDateBetween(from, to);
            logger.info("queriedMatchList: " + queriedMatchList);
            for (Match match : matches) {
                if (isUpdate(match, queriedMatchList)) {
                    rankingService.updateRanking(match);
                    matchRepository.save(match);
                }
            }
        }
    }

    public List<Match> findByRound(Integer round) {
        List<Match> matches = new ArrayList<>();
        switch (round) {
            case 1:
                matches = matchRepository.findMatchByMatchDayBetween(1, 5);
                break;
            case 2:
                matches = matchRepository.findMatchByMatchDayBetween(6, 9);
                break;
            case 3:
                matches = matchRepository.findMatchByMatchDayBetween(10, 13);
                break;
            case 4:
                matches = matchRepository.findMatchByMatchDayBetween(14, 17);
                break;
            case 5:
                matches = matchRepository.findMatchByMatchDayBetween(20, 21);
                break;
            case 6:
                matches = matchRepository.findMatchByMatchDayBetween(24, 25);
                break;
            case 7:
                matches = matchRepository.findMatchByMatchDayBetween(28, 29);
                break;
            default:
                logger.debug("No matches found for that day");
                break;
        }
        return matches.stream().sorted(Comparator.comparing(Match::getMatchDate)).collect(Collectors.toList());
    }



    private static boolean isUpdate(Match newMatch, List<Match> queriedMatchList) {
        return queriedMatchList.stream()
                .anyMatch(match -> Objects.equals(match.getMatchDay(), newMatch.getMatchDay()) &&
                        !Objects.equals(match.getFinished(), newMatch.getFinished()) &&
                        Objects.equals(newMatch.getFinished(), "TRUE"));
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + tokenService.getToken());
        return headers;
    }

}
