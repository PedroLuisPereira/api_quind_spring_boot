package com.example.quind.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CampoConException extends RuntimeException{

    public CampoConException(String msg){
        super(msg);
    }
}