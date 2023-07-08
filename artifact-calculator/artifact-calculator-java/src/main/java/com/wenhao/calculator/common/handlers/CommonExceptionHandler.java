package com.wenhao.calculator.common.handlers;

import com.wenhao.calculator.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * CommonExceptionHandler
 *
 * @author: Wenhao Tong
 * @date: 2023/6/16
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    public Response excepitionHandle(Exception e) {
        log.error("Error: {}", e.getMessage());
        return new Response(e.getMessage());
    }
}
