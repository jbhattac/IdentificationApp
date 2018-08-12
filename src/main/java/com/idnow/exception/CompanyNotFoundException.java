package com.idnow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unable to find the company with the id .")
public class CompanyNotFoundException  extends Exception{


	private static final long serialVersionUID = -9009579001545163040L;


	public CompanyNotFoundException(String message)
    {
        super(message);
    }
	

}
