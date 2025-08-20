package de.example.ibanvalidator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.example.ibanvalidator.common.BlackList;
import de.example.ibanvalidator.common.Constants;
import de.example.ibanvalidator.errorhandling.IbanValidationException;
import de.example.ibanvalidator.errorhandling.RestExceptionHandler;
import de.example.ibanvalidator.services.IbanValidationService;
import de.example.ibanvalidator.validation.IbanValidationError;
import de.example.ibanvalidator.validation.IbanValidationResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests on controller level with mocked service. This way we can test the exception handler.
 */
@SpringBootTest
class IbanValidationControllerTest {

    private MockMvc mockMvc;

    @MockitoBean
    private IbanValidationService ibanValidationService;

    @BeforeEach
    void setup() {

        IbanValidationController controller = new IbanValidationController(ibanValidationService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                            .setControllerAdvice(new RestExceptionHandler())
                            .build();
    }

    @Test
    void testValidateIbansFromPdfRemoteOk() throws Exception {

        String jsonResponse = this.mockMvc
                .perform(get("/api/v1/ibanvalidation/validate-remote?url=" + Constants.REMOTE_TEST_FILE_URL_STRING))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var dto = new ObjectMapper().readValue(jsonResponse, IbanValidationResponseDto.class);

        Assertions.assertEquals(IbanValidationResponseDto.EMPTY_RESPONSE, dto);
    }

    @Test
    void testValidateStringThrowsBlacklisted() throws Exception {

        doThrow(createIbanValidationExceptionWithBlacklistedIban())
            .when(ibanValidationService)
            .validateIbansFromText(any(String.class));

        String jsonResponse = this.mockMvc
                .perform(get("/api/v1/ibanvalidation/validate-string?input=irrelevant"))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var dto = new ObjectMapper().readValue(jsonResponse, IbanValidationResponseDto.class);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(1, dto.getValidationErrors().size());
        Assertions.assertEquals(Constants.BLACKLIST_ERROR_MESSAGE, dto.getValidationErrors().getFirst().getErrorMessage());
        Assertions.assertEquals(BlackList.BLACKLISTED1, dto.getValidationErrors().getFirst().getIban());
    }


    private static IbanValidationException createIbanValidationExceptionWithBlacklistedIban() {

        var error = new IbanValidationError(Constants.BLACKLIST_ERROR_MESSAGE, BlackList.BLACKLISTED1);
        return new IbanValidationException(Constants.IBAN_ERROR_MESSAGE, List.of(error));
    }
}
