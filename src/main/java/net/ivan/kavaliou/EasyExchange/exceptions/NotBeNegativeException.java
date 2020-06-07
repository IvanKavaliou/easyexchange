package net.ivan.kavaliou.EasyExchange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotBeNegativeException extends RuntimeException {
    public NotBeNegativeException(String message){
        super(message);
    }
}

