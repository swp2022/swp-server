package com.swp.common.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public abstract class ConflictException extends ApiException {
    public ConflictException(final String message) {
        super(message, CONFLICT);
    }
}
