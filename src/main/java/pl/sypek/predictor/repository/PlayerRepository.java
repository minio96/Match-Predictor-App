package pl.sypek.predictor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sypek.predictor.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Override
    Optional<Player> findById(Long id);

    List<Player> findUserByGroupId(String groupId);

    List<Player> findPlayerByName(String name);

}
