package com.maveric.csp.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.maveric.csp.exceptions.AllExceptions;
import com.maveric.csp.exceptions.CustomerNotFoundException;
import com.maveric.csp.exceptions.DuplicateNameException;

@ControllerAdvice
@RestController
public class AllGlobalExceptionHandler {

	@ExceptionHandler(AllExceptions.class)
	public final ResponseEntity<String> handleAllExceptions(AllExceptions e) {

		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
		return new ResponseEntity<>("Customer not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler(DuplicateNameException.class)
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<String> handleDuplicateNameException(DuplicateNameException ex) {
//		return new ResponseEntity<>("Duplicate name: " + ex.getMessage(), HttpStatus.OK);
//	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return new ResponseEntity<>("An error occurred Unable to save customer: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
}
