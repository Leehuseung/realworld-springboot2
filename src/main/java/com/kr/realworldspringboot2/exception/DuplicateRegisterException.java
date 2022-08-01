package com.kr.realworldspringboot2.exception;

public class DuplicateRegisterException extends RuntimeException{
    public DuplicateRegisterException(String message){
        super(message);
    }
}
