package pl.sypek.predictor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sypek.predictor.service.PlayerService;
import pl.sypek.predictor.service.RegisterService;

import java.security.Principal;

@Controller
public class LoginController {
    final RegisterService registerService;
    final PlayerService playerService;

    public LoginController(RegisterService registerService, PlayerService playerService) {
        this.registerService = registerService;
        this.playerService = playerService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("success")
    public String login(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "home";
    }

    @RequestMapping("login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping("logout")
    public String getLogout() {
        return "logout";
    }
}
