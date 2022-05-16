package com.swp.study.Exception;

import com.swp.common.exception.NotFoundException;

public class StudyNotFoundException extends NotFoundException {
    public StudyNotFoundException(String message){ super(message); }
}
