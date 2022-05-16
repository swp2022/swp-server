package com.swp.board.exception;

import com.swp.common.exception.ForbiddenException;

public class BoardForbiddenException extends ForbiddenException {

    public BoardForbiddenException(String message){ super(message); }
}
