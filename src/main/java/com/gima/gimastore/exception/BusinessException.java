package com.gima.gimastore.exception;

public class BusinessException extends Exception{

	
	private static final long serialVersionUID = 1L;
	
	public BusinessException() {
	}
	public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
	public BusinessException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
	public BusinessException(String message) {
        super(message);
    }
	
}
