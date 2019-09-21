package com.kakaopay.homework.internetbanking.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Method Not Allowed"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    SOCIAL_TYPE_NOT_EXIST(400, "C007", "Social Type Not Exist"),
    ACCESS_TOKEN_NOT_EXIST(401, "C008", "Access Token Not Exist"),
    ACCESS_TOKEN_EXPIRED(401, "C009", "Access Token Expired"),
    DATA_NOT_EXIST(204, "C010", "Data Not Exist"),
    INVALID_AUTHORIZATION_HEADER(401, "C011", "Invalid Authorization Header"),
    ACCESS_TOKEN_NOT_VALID(401, "C012", "Access Token Not Valid"),
    // Member
    USER_ID_DUPLICATION(400, "M001", "User Id is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),
    USER_NOT_FOUND(400, "M003", "User Not Found");

    private final String code;
    private final String message;
    private int status;
    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
