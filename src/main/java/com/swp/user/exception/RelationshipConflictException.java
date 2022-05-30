package com.swp.user.exception;

import com.swp.common.exception.ConflictException;

public class RelationshipConflictException extends ConflictException {
	public RelationshipConflictException(String message) {
		super(message);
	}
}
