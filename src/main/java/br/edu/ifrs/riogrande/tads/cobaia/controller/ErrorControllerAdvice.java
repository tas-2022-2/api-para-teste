package br.edu.ifrs.riogrande.tads.cobaia.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.edu.ifrs.riogrande.tads.cobaia.app.services.ServiceException;

@ControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {


	@ExceptionHandler(ServiceException.class)
	protected ResponseEntity<Map<String, List<String>>> trataServiceException(ServiceException ex) {

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
			.body(Map.of("erros", Collections.singletonList(ex.getMessage())));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Map<String, List<String>>> trataConstraintViolationException(ConstraintViolationException ex) {

		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		List<String> erros = violations.stream()
				.map(v -> v.getMessage())// .toList();
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(Map.of("erros", erros));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		List<String> erros = ex.getFieldErrors().stream()
				.map(e -> e.getDefaultMessage())// .toList();
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(Map.of("erros", erros));
	}



}
