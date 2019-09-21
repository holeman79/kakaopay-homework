package com.kakaopay.homework.internetbanking.exception;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}