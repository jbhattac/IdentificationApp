/**
 * 
 */
package com.idnow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author JBhattacharjee
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unable to create a duplicate company with same Id .")
public class DuplicateCompanyException  extends Exception{
	
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 2673833130664234213L;

	public DuplicateCompanyException(String message)
    {
        super(message);
    }

}
