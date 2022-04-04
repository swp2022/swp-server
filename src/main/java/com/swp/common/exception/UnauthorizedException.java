package com.swp.common.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ResponseStatus(UNAUTHORIZED)
public abstract class UnauthorizedException extends ApiException {
    public UnauthorizedException(final String message) {
        super(message, UNAUTHORIZED);
    }
}
