package pl.sypek.predictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllMatchesResponse {
    String status;
    @JsonProperty(value = "data")
    List<Match> matchList;

    public AllMatchesResponse() {
    }
}
