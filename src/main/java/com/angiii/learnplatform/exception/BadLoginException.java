package com.angiii.learnplatform.exception;

import lombok.Data;

@Data
public class BadLoginException extends RuntimeException {

    private String msg;

    public BadLoginException(String e) {
        super(e);
    }

    public BadLoginException(String msg, String e) {
        super(e);
        this.msg = msg;
    }
}
