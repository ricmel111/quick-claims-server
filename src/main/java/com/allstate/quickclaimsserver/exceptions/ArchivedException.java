package com.allstate.quickclaimsserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ArchivedException extends Exception{
    public ArchivedException(String message) { super(message); }
}
