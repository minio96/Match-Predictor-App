package pl.sypek.predictor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.sypek.predictor.model.Prediction;
import pl.sypek.predictor.repository.PredictionRepository;

import java.util.List;

@Controller
public class PredictionController {

    final PredictionRepository predictionRepository;


    public PredictionController(PredictionRepository predictionRepository) {
        this.predictionRepository = predictionRepository;
    }

    @PutMapping("predictions")
    @ResponseBody
    void addPredictions(@RequestBody List<Prediction> predictionList) {
        predictionRepository.saveAll(predictionList);
    }


}
