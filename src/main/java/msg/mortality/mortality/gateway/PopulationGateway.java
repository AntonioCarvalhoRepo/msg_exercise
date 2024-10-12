package msg.mortality.mortality.gateway;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import msg.mortality.mortality.dto.MortalityRequestDTO;
import msg.mortality.mortality.dto.MortalityResponseDTO;
import msg.mortality.mortality.dto.PopulationResponseDTO;
import msg.mortality.mortality.service.MortalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/population", produces = MediaType.APPLICATION_JSON_VALUE)
public class PopulationGateway {

    @Hidden
    @Operation(summary = "Get Population of that year, by given country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Population of said country",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PopulationResponseDTO.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content) })
    @GetMapping(path = "/{country}")
    public PopulationResponseDTO getPopulationByCountry(@PathVariable String country){
        return switch (country) {
            case "pt" -> new PopulationResponseDTO(1000, 1000);
            case "en" -> new PopulationResponseDTO(2000, 2000);
            default -> new PopulationResponseDTO(3000, 3000);
        };
    }
}
