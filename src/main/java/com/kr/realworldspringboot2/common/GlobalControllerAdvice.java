package com.kr.realworldspringboot2.common;

import com.kr.realworldspringboot2.exception.DuplicateRegisterException;
import com.kr.realworldspringboot2.exception.NotUserOwnException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(DuplicateRegisterException.class)
    public ResponseEntity<Object> duplicateCustomException(DuplicateRegisterException ce) {
        Map<String, Object> body = new LinkedHashMap<>();

        JSONObject json = new JSONObject();
        JSONObject errors = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonArray.add("has already been taken");

        errors.put(ce.getMessage(),jsonArray);

        json.put("errors",errors);
        body.put("errors", errors);

        return new ResponseEntity<  >(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotUserOwnException.class)
    public ResponseEntity<Object> NotUserOwnException(NotUserOwnException ce) {
        Map<String, Object> body = new LinkedHashMap<>();

        JSONObject json = new JSONObject();
        JSONObject errors = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonArray.add("confirm your "+ce.getMessage()+" id");

        errors.put(ce.getMessage(),jsonArray);

        json.put("errors",errors);
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}