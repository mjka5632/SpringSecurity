package com.imooc.web.controller;

import com.imooc.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Contoller层的错误处理器
 */
//@ControllerAdvice
    @RestControllerAdvice
public class ControllerExceptionHandler {
    /**
     * @ExceptionHandler(UserNotExistException.class)处理这个Exception类的异常
     * @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)定义返回的状态码
     * @param ex 通过类自己制定返回的错误格式
     * @return
     */
    @ExceptionHandler(UserNotExistException.class)
//    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handlerUserNotExistException(UserNotExistException ex){
        Map<String, Object> map = new HashMap<>();
        map.put("msg",ex.getMessage());
        map.put("id",ex.getId());
        return map;
    }
}
