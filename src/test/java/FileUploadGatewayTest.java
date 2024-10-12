import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.DataInputStream;

import msg.mortality.mortality.gateway.FileUploadGateway;
import msg.mortality.mortality.service.MortalityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {FileUploadGateway.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class FileUploadGatewayTest {
    @Autowired
    private FileUploadGateway fileUploadGateway;

    @MockBean
    private MortalityService mortalityService;

    /**
     * Method under test: {@link FileUploadGateway#uploadCSV(MultipartFile)}
     */
    @Test
    void testUploadCSV() throws Exception {
        // Arrange
        DataInputStream contentStream = mock(DataInputStream.class);
        when(contentStream.readAllBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        doNothing().when(contentStream).close();
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/upload");
        postResult.accept("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("file",
                String.valueOf(new MockMultipartFile("Name", contentStream)));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(fileUploadGateway)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }
}
