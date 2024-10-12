package msg.mortality.mortality.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MortalityRequestDTO {
    @JsonProperty("year")
    @NotNull(message = "Year is required")
    private int year;

    @JsonProperty("country")
    @NotNull(message = "Country is required")
    private String country;

    @JsonProperty("maleDeathRate")
    @DecimalMin(value = "0.00", message = "The value must be positive with two decimal houses")
    private double maleDeathRate;

    @JsonProperty("femaleDeathRate")
    @DecimalMin(value = "0.00", message = "The value must be positive with two decimal houses")
    private double femaleDeathRate;
}
