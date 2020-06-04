package net.ivan.kavaliou.EasyExchange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ExsistException extends RuntimeException {

    public ExsistException(String message){
        super(message);
    }
}