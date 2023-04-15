package pl.sypek.predictor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.sypek.predictor.model.Match;
import pl.sypek.predictor.model.MatchForm;
import pl.sypek.predictor.repository.MatchRepository;
import pl.sypek.predictor.service.MatchService;
import pl.sypek.predictor.service.PlayerService;

import java.security.Principal;
import java.util.List;

@Controller
public class MatchController {
    public static final Logger logger = LoggerFactory.getLogger(MatchController.class);

    final MatchRepository matchRepository;
    final MatchService matchService;
    final PlayerService playerService;

    public MatchController(MatchRepository matchRepository, MatchService matchService, PlayerService playerService) {
        this.matchRepository = matchRepository;
        this.matchService = matchService;
        this.playerService = playerService;
    }

    @GetMapping("match")
    @ResponseBody
    public List<Match> getMatchesByMatchDay(@RequestParam Integer matchDay) {
        return matchRepository.findMatchByMatchDay(matchDay);
    }

    @GetMapping(value = "match/page/{round}")
    public String getPredictionPage(@PathVariable Integer round, Model model) {
        MatchForm matchForm = new MatchForm();
        matchForm.setMatchList(matchService.findByRound(round));
        model.addAttribute("matches", matchForm);
        model.addAttribute("matchesToWrite", matchForm);
        return "predictions";
    }

    @RequestMapping(value = "match/prediction", method = RequestMethod.POST)
    @ResponseBody
    public String handleForm(@ModelAttribute MatchForm matchesToWrite, Principal principal) {
//        Long user = playerService.getPlayerByName(principal.getName()).getPlayer_id();
        logger.info("Put matches : {}", matchesToWrite);
        return "OK";
    }
}
