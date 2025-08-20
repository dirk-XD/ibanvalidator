package de.example.ibanvalidator.errorhandling;

import de.example.ibanvalidator.validation.IbanValidationError;
import de.example.ibanvalidator.validation.IbanValidationResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;

/**
 * Global exception handler for the application to manage exceptions raised during HTTP requests.
 * Provides custom handling for specific exceptions and formats appropriate individual responses and HTTP status
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<IbanValidationResponseDto> handleException(IOException e) {

        log.error("IO-Exception:", e);
        return buildResponse(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IbanValidationException.class)
    public ResponseEntity<IbanValidationResponseDto> handleException(IbanValidationException e) {

        log.error("IbanValidationException:", e);
        return buildResponse(e.getMessage(), e.getIbanValidationErrors(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle all exceptions, which are not treated individually
     * @param e the exception
     * @return a {@link  ResponseEntity} with the exception's message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<IbanValidationResponseDto> handleException(Exception e) {

        log.error("Unhandled exception type:", e);
        return buildResponse(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<IbanValidationResponseDto> buildResponse(String message, List<IbanValidationError> errorList,
                                                                    HttpStatus status) {

        IbanValidationResponseDto response = new IbanValidationResponseDto(message, errorList);
        return new ResponseEntity<>(response, status);
    }
}
