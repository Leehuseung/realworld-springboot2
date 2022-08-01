package com.kr.realworldspringboot2.common;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;

@ControllerAdvice
public class ValidationAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        JSONObject body = new JSONObject();
        JSONArray errors = new JSONArray();

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        for (int i = 0; i < allErrors.size(); i++) {
            errors.add(allErrors.get(i).getDefaultMessage());
        }

        body.put("errors", errors);
        body.put("timestamp", new Date());
        body.put("status", status.value());

        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }
}

