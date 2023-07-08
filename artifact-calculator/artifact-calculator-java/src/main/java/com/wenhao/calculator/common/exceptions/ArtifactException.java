package com.wenhao.calculator.common.exceptions;

/**
 * ArtifactException
 *
 * @author: Wenhao Tong
 * @date: 2023/6/16
 */
public class ArtifactException extends RuntimeException {
    private final String message;

    public ArtifactException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
