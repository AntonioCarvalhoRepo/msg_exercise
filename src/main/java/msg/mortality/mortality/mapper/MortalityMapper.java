package msg.mortality.mortality.mapper;

import msg.mortality.mortality.dto.MortalityRequestDTO;
import msg.mortality.mortality.dto.MortalityResponseDTO;
import msg.mortality.mortality.entity.Mortality;
import org.springframework.stereotype.Component;

@Component
public class MortalityMapper {

    public Mortality mapRequestToEntity (MortalityRequestDTO mortalityRequestDTO){
        Mortality entity = new Mortality();
        entity.setCountry(mortalityRequestDTO.getCountry());
        entity.setMaleDeathRate(mortalityRequestDTO.getMaleDeathRate());
        entity.setFemaleDeathRate(mortalityRequestDTO.getFemaleDeathRate());
        return  entity;
    }

    public MortalityResponseDTO mapEntityToResponseDTO(Mortality mortality){
        MortalityResponseDTO responseDTO = new MortalityResponseDTO();
        responseDTO.setCountry(mortality.getCountry());
        responseDTO.setMaleDeathRate(mortality.getMaleDeathRate());
        responseDTO.setFemaleDeathRate(mortality.getFemaleDeathRate());
        return responseDTO;
    }
}
