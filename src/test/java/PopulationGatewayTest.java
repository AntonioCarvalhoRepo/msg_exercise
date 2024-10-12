import msg.mortality.mortality.gateway.PopulationGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PopulationGateway.class})
@ExtendWith(SpringExtension.class)
class PopulationGatewayTest {
    @Autowired
    private PopulationGateway populationGateway;

    /**
     * Method under test: {@link PopulationGateway#getPopulationByCountry(String)}
     */
    @Test
    void testGetPopulationByCountry() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/population/{country}", "GB");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(populationGateway)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"malePopulation\":3000,\"femalePopulation\":3000}"));
    }
}
