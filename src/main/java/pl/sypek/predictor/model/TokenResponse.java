package pl.sypek.predictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    String status;
    @JsonProperty(value = "data")
    Token token;

    @Getter
    @Setter
    public static class Token {
        String token;
    }
}
