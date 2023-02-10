package com.allstate.quickclaimsserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidFieldException extends Exception{
    public InvalidFieldException(String message) { super(message); }
}
