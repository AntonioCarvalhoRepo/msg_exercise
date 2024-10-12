package msg.mortality.mortality.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import msg.mortality.mortality.dto.MortalityRequestDTO;
import msg.mortality.mortality.dto.MortalityResponseDTO;
import msg.mortality.mortality.dto.PopulationResponseDTO;
import msg.mortality.mortality.entity.Mortality;
import msg.mortality.mortality.mapper.MortalityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DynamicTableServiceImpl implements DynamicTableService{
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MortalityMapper mapper;
    
    @Autowired
    RestTemplate restTemplate;

    @Value("${population.endpoint}")
    String populationEndpoint;

    /**
     * Method to createMortalityRecord table dynamically based on the year.
     */
    @Transactional
    public void createYearlyTable(String defaultTableName, int year) {
        String tableName = defaultTableName + year;

        // SQL query to createMortalityRecord the table dynamically
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "country VARCHAR(255), " +
                "maleDeathRate DECIMAL(10,2), " +
                "femaleDeathRate DECIMAL(10,2), " +
                "malePopulation INT, " +
                "femalePopulation INT, " +
                "PRIMARY KEY (id)" +
                ")";

        // Execute the query
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Override
    @Transactional
    /**
     * Insert Mortality record according to the request data and year
     */
    public void saveMortalityRecord(String defaultTableName,  MortalityRequestDTO requestDTO) {
        //Request PopulationData to mock service
        String url = populationEndpoint + requestDTO.getCountry().toLowerCase();

        PopulationResponseDTO populationResponse = restTemplate.getForObject(url, PopulationResponseDTO.class);


        Mortality mortality = mapper.mapRequestToEntity(requestDTO);

        String tableName = defaultTableName + requestDTO.getYear();
        String insertSql = "INSERT INTO " + tableName + " (country,maleDeathRate,femaleDeathRate,malePopulation,femalePopulation) VALUES (:country,:maleDeathRate,:femaleDeathRate,:malePopulation,:femalePopulation)";

        // Create a native query
        Query query = entityManager.createNativeQuery(insertSql);

        // Set query parameters
        query.setParameter("country", mortality.getCountry());
        query.setParameter("maleDeathRate", mortality.getMaleDeathRate());
        query.setParameter("femaleDeathRate", mortality.getFemaleDeathRate());
        query.setParameter("malePopulation", populationResponse != null ? populationResponse.getMalePopulation() : 0);
        query.setParameter("femalePopulation", populationResponse != null ? populationResponse.getFemalePopulation() : 0);

        // Execute the query
        query.executeUpdate();
    }

    @Override
    /**
     * Find records by year
     */
    public List<MortalityResponseDTO> findByTableNameAndYear(String table) {
        String sql = "SELECT country,maleDeathRate,femaleDeathRate FROM " + table;

        try {
            // Create query
            List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();

            if (!results.isEmpty()) {
                return results.stream().map(row -> {
                    String country = (String) row[0];
                    double maleDeathRate = new BigDecimal(String.valueOf(row[1])).doubleValue();
                    double femaleDeathRate = new BigDecimal(String.valueOf(row[2])).doubleValue();
                    return new MortalityResponseDTO(country, maleDeathRate, femaleDeathRate);
                }).collect(Collectors.toList());
            } else {
                return null;
            }
        }catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Table not found: " + table);
        }
    }

    @Override
    @Transactional
    public void deleteRecordsInTable(String defaultTableName, int year) {
        String table = defaultTableName + year;

        String sql = "DELETE FROM " + table;

        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
