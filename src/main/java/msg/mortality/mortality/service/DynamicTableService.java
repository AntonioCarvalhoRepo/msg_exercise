package msg.mortality.mortality.service;

import msg.mortality.mortality.dto.MortalityRequestDTO;
import msg.mortality.mortality.dto.MortalityResponseDTO;
import msg.mortality.mortality.entity.Mortality;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicTableService {
    public void createYearlyTable(String defaultTableName, int year);

    public void saveMortalityRecord(String defaultTableName, MortalityRequestDTO requestDTO);

    List<MortalityResponseDTO> findByTableNameAndYear(String tableName);

    void deleteRecordsInTable(String defaultTableName, int year);
}
