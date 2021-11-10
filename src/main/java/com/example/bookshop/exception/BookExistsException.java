package com.example.bookshop.exception;

public class BookExistsException extends Exception {
    public BookExistsException(String message) {
        super(message);
    }
}
