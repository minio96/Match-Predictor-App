package pl.sypek.predictor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sypek.predictor.exception.ObjectNotFoundException;
import pl.sypek.predictor.model.Player;
import pl.sypek.predictor.model.PlayerForm;
import pl.sypek.predictor.repository.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {
    final PlayerRepository playerRepository;
    final PasswordEncoder passwordEncoder;
    
    Logger logger = LoggerFactory.getLogger(PlayerService.class);

    public PlayerService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User with id  " + id + " not found"));
    }

    public List<Player> getPlayersByGroupId(String groupId) {
        List<Player> playersById = playerRepository.findUserByGroupId(groupId);
        logger.debug("Getting players by id {}", playersById);
        return playerRepository.findUserByGroupId(groupId);
    }

    public Player getPlayerByName(String name) {
        return playerRepository.findPlayerByName(name).stream().distinct().findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Player with name " + name + " not found"));
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player addPlayer(PlayerForm playerForm) {
        logger.debug("Adding player: " + playerForm.getName());
        Player player = Player.builder()
                .name(playerForm.getName())
                .points(0)
                .groupId(playerForm.getGroupId())
                .build();
        return playerRepository.save(player);
    }

    public void removePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    //TODO update player

}
