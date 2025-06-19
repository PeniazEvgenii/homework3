package ru.aston.hometask.exception;

public class FileServiceException extends RuntimeException {

    public FileServiceException() {
    }

    public FileServiceException(String message) {
        super(message);
    }

    public FileServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
