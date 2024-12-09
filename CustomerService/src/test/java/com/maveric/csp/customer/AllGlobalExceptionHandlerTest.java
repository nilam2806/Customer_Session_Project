package com.maveric.csp.customer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.maveric.csp.exceptionhandler.AllGlobalExceptionHandler;
import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.CustomerNotFoundException;

public class AllGlobalExceptionHandlerTest {
	// Create a mock BindingResult
	BindingResult bindingResult = mock(BindingResult.class);

	// Create a mock MethodParameter (if needed)
	MethodParameter methodParameter = mock(MethodParameter.class);

	// Construct the MethodArgumentNotValidException
	MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);

    @Test
    public void testHandleAllExceptions() {
        AllExceptions exception = new AllExceptions("Test exception");
        AllGlobalExceptionHandler handler = new AllGlobalExceptionHandler();
        ResponseEntity<String> response = handler.handleAllExceptions(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Test exception", response.getBody());
    }

    @Test
    public void testHandleValidationExceptions() {
        FieldError fieldError = new FieldError("objectName", "fieldName", "error message");
        BindException bindException = new BindException(new Object(), "objectName");
        bindException.addError(fieldError);
//        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindException);
        AllGlobalExceptionHandler handler = new AllGlobalExceptionHandler();
        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("fieldName", "error message");
//        assertEquals(expectedErrors, handler.handleValidationExceptions(exception));
    }

    @Test
    public void testHandleCustomerNotFoundException() {
        CustomerNotFoundException exception = new CustomerNotFoundException("Customer not found");
        AllGlobalExceptionHandler handler = new AllGlobalExceptionHandler();
        ResponseEntity<String> response = handler.handleCustomerNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found: Customer not found", response.getBody());
    }

    @Test
    public void testHandleGeneralException() {
        Exception exception = new Exception("Test general exception");
        AllGlobalExceptionHandler handler = new AllGlobalExceptionHandler();
        ResponseEntity<String> response = handler.handleGeneralException(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred Unable to save customer: Test general exception", response.getBody());
    }
}
