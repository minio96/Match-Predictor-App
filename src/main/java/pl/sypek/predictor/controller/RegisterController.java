package pl.sypek.predictor.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sypek.predictor.model.PlayerForm;
import pl.sypek.predictor.service.PlayerService;
import pl.sypek.predictor.service.RegisterService;


@Controller
public class RegisterController {
    final RegisterService registerService;
    final PlayerService playerService;

    public RegisterController(RegisterService registerService, PlayerService playerService) {
        this.registerService = registerService;
        this.playerService = playerService;
    }

    @RequestMapping("/register")
    public String addPlayer(Model model) {
        model.addAttribute("player", new PlayerForm());
        return "register";
    }

    @PostMapping("/register")
    public String addPlayer(@ModelAttribute("player") @Valid PlayerForm player, Model model) {
        registerService.registerPlayer(player);
        playerService.addPlayer(player);
        return "registerSuccess";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removePlayer(@PathVariable Long id) {
        registerService.unregisterPlayer(id);
        playerService.removePlayer(id);
        return new ResponseEntity<>("Player " + id + " unregistered and removed", HttpStatus.OK);
    }
}
