import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import msg.mortality.mortality.dto.MortalityRequestDTO;
import msg.mortality.mortality.gateway.MortalityGateway;
import msg.mortality.mortality.service.MortalityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MortalityGateway.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MortalityGatewayTest {
    @Autowired
    private MortalityGateway mortalityGateway;

    @MockBean
    private MortalityService mortalityService;

    /**
     * Method under test:
     * {@link MortalityGateway#createMortalityRecord(MortalityRequestDTO)}
     */
    @Test
    void testCreateMortalityRecord() throws Exception {
        // Arrange
        doNothing().when(mortalityService).createMortalityRecord(Mockito.<MortalityRequestDTO>any());

        MortalityRequestDTO mortalityRequestDTO = new MortalityRequestDTO();
        mortalityRequestDTO.setCountry("GB");
        mortalityRequestDTO.setFemaleDeathRate(10.0d);
        mortalityRequestDTO.setMaleDeathRate(10.0d);
        mortalityRequestDTO.setYear(1);
        String content = (new ObjectMapper()).writeValueAsString(mortalityRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/mortality")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(mortalityGateway)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    /**
     * Method under test: {@link MortalityGateway#getMortalityRatesByYear(int)}
     */
    @Test
    void testGetMortalityRatesByYear() throws Exception {
        // Arrange
        when(mortalityService.getMortalityRatesByYear(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mortality/{year}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(mortalityGateway)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
