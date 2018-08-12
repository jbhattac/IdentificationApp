package com.idnow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unable to create a duplicate Identification with same Id.")
public class DuplicateIdentificationException extends Exception{


	private static final long serialVersionUID = 1081544078237671602L;
	
	public DuplicateIdentificationException(String message)
    {
        super(message);
    }

}
