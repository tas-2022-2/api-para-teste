package br.edu.ifrs.riogrande.tads.cobaia.app.services;

public class ServiceException extends RuntimeException {

	public ServiceException(String message) {
		super(message);
	}
	// Pattern? Decorator/Wrapper
	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
