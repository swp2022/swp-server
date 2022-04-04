package com.swp.common.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public abstract class BadRequestException extends ApiException {
    public BadRequestException(final String message) {
        super(message, BAD_REQUEST);
    }
}
