package pl.sypek.predictor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class PredictorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PredictorApplication.class, args);
    }

}
