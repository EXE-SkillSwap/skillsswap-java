package com.skillswap.server.enums;

public enum ErrorCode {
    NOT_MEMBERSHIP(3003,"Chưa đăng ký thành viên");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
