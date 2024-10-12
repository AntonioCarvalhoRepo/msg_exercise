package msg.mortality.mortality.service;

import msg.mortality.mortality.dto.MortalityRequestDTO;
import msg.mortality.mortality.dto.MortalityResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MortalityService {
    void deleteMortalityRecords(int year);

    void createMortalityRecord(MortalityRequestDTO record);

    List<MortalityResponseDTO> getMortalityRatesByYear(int year);
}
