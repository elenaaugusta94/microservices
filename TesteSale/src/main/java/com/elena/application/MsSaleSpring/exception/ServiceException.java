package com.elena.application.MsSaleSpring.exception;

public class ServiceException extends Exception{
	public ServiceException() {
		super("An internal service error occured");
	}
}
