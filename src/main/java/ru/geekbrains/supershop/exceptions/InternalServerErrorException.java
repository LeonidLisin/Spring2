package ru.geekbrains.supershop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InternalServerErrorException extends Exception {

    public InternalServerErrorException(String message) {
        super(message);
    }

}
