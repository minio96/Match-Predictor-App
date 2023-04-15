package pl.sypek.predictor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.sypek.predictor.model.Player;
import pl.sypek.predictor.service.MatchService;
import pl.sypek.predictor.service.RankingService;

import java.text.ParseException;
import java.util.List;

@Controller
public class RankingController {

    final RankingService rankingService;
    final MatchService matchService;

    public RankingController(RankingService rankingService, MatchService matchService) {
        this.rankingService = rankingService;
        this.matchService = matchService;
    }

    @GetMapping("ranking")
    @ResponseBody
    public List<Player> getRanking() {
        return rankingService.getRanking();
    }

    @GetMapping("ranking/page")
    public String getRankingPage(Model model) {
        model.addAttribute("players", rankingService.getRanking());
        return "ranking";
    }

    @GetMapping("ranking/update")
    @ResponseBody
    public ResponseEntity<String> updateRanking() throws ParseException {
        matchService.updateMatchByDate();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
