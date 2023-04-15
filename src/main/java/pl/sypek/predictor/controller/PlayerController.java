package pl.sypek.predictor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sypek.predictor.exception.ObjectNotFoundException;
import pl.sypek.predictor.model.Player;
import pl.sypek.predictor.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{playerId}")
    public Player getPlayer(@PathVariable Long playerId) {
        return playerService.getPlayerById(playerId);
    }

    @GetMapping("/group/{groupId}")
    public List<Player> getUsersByGroup(@PathVariable String groupId) {
        return playerService.getPlayersByGroupId(groupId);
    }

    @PostMapping("/")
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        Player created = playerService.addPlayer(player);
        return new ResponseEntity<>("Player " + created.getPlayer_id() + " added", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removePlayer(@PathVariable Long id) {
        playerService.removePlayer(id);
        return new ResponseEntity<>("Player " + id + " removed", HttpStatus.OK);
    }

    @ExceptionHandler(value = ObjectNotFoundException.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
