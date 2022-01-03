package com.idolcollector.idolcollector.advice.exception;

public class CNotLoginedException extends RuntimeException{

    public CNotLoginedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CNotLoginedException(String msg) {
        super(msg);
    }

    public CNotLoginedException() {
        super();
    }
}
