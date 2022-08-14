package com.kr.realworldspringboot2.exception;

public class NotUserOwnException extends RuntimeException {
    public NotUserOwnException(String message){
        super(message);
    }
}
