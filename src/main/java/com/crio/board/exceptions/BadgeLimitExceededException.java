package com.crio.board.exceptions;

public class BadgeLimitExceededException extends Exception {
    public BadgeLimitExceededException(String message) {
        super(message);
    }
}
