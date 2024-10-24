package saw.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import saw.models.RecaptchaResponse;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class RecaptchaController {
    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${recaptcha.verifyUrl}")
    private String recaptchaVerifyUrl;

    @PostMapping("/recaptcha-verify")
    private RecaptchaResponse verifyRecaptcha(@RequestParam String frontToken) {
        RestTemplate restTemplate = new RestTemplate();

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(recaptchaVerifyUrl)
                .queryParam("secret", recaptchaSecret)
                .queryParam("response", frontToken)
                .toUriString();

        // Send request to Google
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(urlTemplate, null, Object.class);

        RecaptchaResponse recaptchaResponse = new RecaptchaResponse();

        if (responseEntity.getBody() instanceof Map) {
            Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
            recaptchaResponse.setSuccess((Boolean) responseBody.get("success"));
            recaptchaResponse.setChallenge_ts((String) responseBody.get("challenge_ts"));
            recaptchaResponse.setHostname((String) responseBody.get("hostname"));
            recaptchaResponse.setScore((Double) responseBody.get("score"));
            recaptchaResponse.setAction((String) responseBody.get("action"));
            recaptchaResponse.setErrorCodes((ArrayList<String>) responseBody.get("error-codes"));
        }

        return recaptchaResponse;
    }
}
