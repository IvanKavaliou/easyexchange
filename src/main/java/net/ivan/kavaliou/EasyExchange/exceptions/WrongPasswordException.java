package net.ivan.kavaliou.EasyExchange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String message){
        super(message);
    }
}
