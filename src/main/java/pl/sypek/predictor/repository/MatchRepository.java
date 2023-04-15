package pl.sypek.predictor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sypek.predictor.model.Match;

import java.util.Date;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findMatchByMatchDay(Integer matchDay);

    List<Match> findMatchByMatchDayBetween(Integer matchDayFrom, Integer matchDayTo);

    List<Match> findByMatchDateBetween(Date from, Date to);
}
