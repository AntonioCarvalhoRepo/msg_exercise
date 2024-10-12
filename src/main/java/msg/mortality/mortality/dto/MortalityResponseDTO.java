package msg.mortality.mortality.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MortalityResponseDTO {
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