package com.idolcollector.idolcollector.advice.exception;

public class CBundleNotFoundException extends RuntimeException{
    public CBundleNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CBundleNotFoundException(String msg) {
        super(msg);
    }

    public CBundleNotFoundException() {
        super();
    }
}
