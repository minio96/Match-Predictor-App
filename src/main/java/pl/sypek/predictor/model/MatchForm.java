package pl.sypek.predictor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MatchForm {
    List<Match> matchList;
}
