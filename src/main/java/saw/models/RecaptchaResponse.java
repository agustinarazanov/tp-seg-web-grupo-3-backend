package saw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecaptchaResponse {
    private Boolean success;
    private String challenge_ts;
    private String hostname;
    private Double score;
    private String action;
    private ArrayList<String> errorCodes;
}
