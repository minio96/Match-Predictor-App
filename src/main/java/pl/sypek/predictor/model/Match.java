package pl.sypek.predictor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "match")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
    @Id
    @JsonProperty(value = "id")
    Long id;
    @Column(name = "match_date")
    @JsonProperty(value = "local_date")
//    @JsonDeserialize(using = DateDeserializers.SqlDateDeserializer.class)
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm")
    Date matchDate;
    @JsonProperty(value = "home_team_en")
    String home;
    @JsonProperty(value = "away_team_en")
    String away;
    @JsonProperty(value = "home_score")
    String homeScore;
    @JsonProperty(value = "away_score")
    String awayScore;
    @JsonProperty(value = "home_flag")
    String homeFlagLink;
    @JsonProperty(value = "away_flag")
    String awayFlagLink;
    String finished;
    @JsonProperty(value = "matchday")
    Integer matchDay;

    Integer homePrediction;

    Integer awayPrediction;

    public Match() {
    }
}
