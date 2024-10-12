package msg.mortality.mortality.gateway;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import msg.mortality.mortality.dto.MortalityRequestDTO;
import msg.mortality.mortality.dto.MortalityResponseDTO;
import msg.mortality.mortality.service.MortalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/mortality", produces = MediaType.APPLICATION_JSON_VALUE)
public class MortalityGateway {

    @Autowired
    MortalityService mortalityService;

    @Operation(summary = "Create Mortality Record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Mortality Record created"),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content) })
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createMortalityRecord(@Valid @RequestBody MortalityRequestDTO record){
        mortalityService.createMortalityRecord(record);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get Mortality Records by year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of Mortality Rates for a user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content) })
    @GetMapping(path = "/{year}")
    public List<MortalityResponseDTO> getMortalityRatesByYear(@PathVariable int year){
        return  mortalityService.getMortalityRatesByYear(year);
    }
}
