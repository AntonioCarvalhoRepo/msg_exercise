package msg.mortality.mortality.service;

import msg.mortality.mortality.dto.MortalityRequestDTO;
import msg.mortality.mortality.dto.MortalityResponseDTO;
import msg.mortality.mortality.entity.MortalityRepository;
import msg.mortality.mortality.mapper.MortalityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MortalityServiceImpl implements MortalityService{
    @Autowired
    DynamicTableService dynamicTableService;

    @Autowired
    MortalityMapper mortalityMapper;

    @Autowired
    MortalityRepository mortalityRepository;

    @Value("${mortality.default.table.name}")
    String defaultTableName;


    @Override
    public void deleteMortalityRecords(int year) {
        dynamicTableService.deleteRecordsInTable(defaultTableName,year);
    }

    @Override
    public void createMortalityRecord(MortalityRequestDTO record) {
        dynamicTableService.createYearlyTable(defaultTableName,record.getYear());
        dynamicTableService.saveMortalityRecord(defaultTableName,record);
    }

    @Override
    public List<MortalityResponseDTO> getMortalityRatesByYear(int year) {
        return dynamicTableService.findByTableNameAndYear(defaultTableName + year);
    }
}
