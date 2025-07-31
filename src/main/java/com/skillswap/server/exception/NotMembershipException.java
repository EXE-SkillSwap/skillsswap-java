package com.skillswap.server.exception;

public class NotMembershipException extends RuntimeException{
    public NotMembershipException(String message) {
        super(message);
    }
}
