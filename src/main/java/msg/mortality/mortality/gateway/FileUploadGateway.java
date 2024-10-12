package msg.mortality.mortality.gateway;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import msg.mortality.mortality.dto.MortalityRequestDTO;
import msg.mortality.mortality.service.MortalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileUploadGateway {
    @Autowired
    MortalityService mortalityService;

    @Operation(summary = "Upload File to createMortalityRecord new mortality DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No Content",
                    content =  @Content),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<?> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<MortalityRequestDTO> mortalityRecords = new ArrayList<MortalityRequestDTO>();
        Set<Integer> mortalityYearsToClear = new LinkedHashSet<Integer>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            // Skip header if necessary
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                mortalityYearsToClear.add(Integer.parseInt(data[0]));

                // Create User object from CSV data
                MortalityRequestDTO mortalityRecord = new MortalityRequestDTO();
                mortalityRecord.setYear(Integer.parseInt(data[0]));
                mortalityRecord.setCountry(data[1]);
                mortalityRecord.setMaleDeathRate(Double.parseDouble(data[2]));
                mortalityRecord.setFemaleDeathRate(Double.parseDouble(data[3]));

                mortalityRecords.add(mortalityRecord);
            }

            //Delete records in each year
            mortalityYearsToClear.forEach(year ->mortalityService.deleteMortalityRecords(year));

            //Add the new mortality records
            mortalityRecords.forEach(record -> mortalityService.createMortalityRecord(record));


            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
