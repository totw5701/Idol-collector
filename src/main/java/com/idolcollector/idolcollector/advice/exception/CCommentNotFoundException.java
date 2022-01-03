package com.idolcollector.idolcollector.advice.exception;

public class CCommentNotFoundException extends RuntimeException{

    public CCommentNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CCommentNotFoundException(String msg) {
        super(msg);
    }

    public CCommentNotFoundException() {
        super();
    }
}
