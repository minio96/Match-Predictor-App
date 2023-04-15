package pl.sypek.predictor.service;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.sypek.predictor.exception.TokenNotFoundException;
import pl.sypek.predictor.model.TokenResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    HashMap<String, String> registeredUsers = new HashMap<>();
    final RestTemplate restTemplate;

    public TokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String token;

    @PostConstruct
    void init(){
        registeredUsers.put("mi.sypek@wp.pl", "dummyPass");
        registeredUsers.put("admin@wp.pl", "dummyPass");
        registeredUsers.put("qwerty@wp.pl", "dummyPass");
        registeredUsers.put("ak@wp.pl", "dummyPass");
        registeredUsers.put("b@wp.pl", "dummyPass");
    }

    public String getToken() {
        return token == null ? getNewToken() : token;
    }

    private  String getNewToken() {
        ResponseEntity<TokenResponse> responseEntity;
        HashMap<String, String> body = new HashMap<>();
        for (Map.Entry<String, String> entry : registeredUsers.entrySet()) {
            body.put("email", entry.getKey());
            body.put("password", entry.getValue());
            responseEntity = restTemplate.postForEntity("/user/login", body, TokenResponse.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody().getToken() != null) {
                token = responseEntity.getBody().getToken().getToken();
                return token;
            }
        }
        throw new TokenNotFoundException("Could not access World Cup api");
    }
}
