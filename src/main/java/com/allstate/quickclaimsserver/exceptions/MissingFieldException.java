package com.allstate.quickclaimsserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MissingFieldException extends Exception {
    public MissingFieldException(String message) {
        super(message);
    }
}
