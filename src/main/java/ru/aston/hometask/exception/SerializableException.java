package ru.aston.hometask.exception;

public class SerializableException extends RuntimeException {

  public SerializableException(String message, Throwable cause) {
    super(message, cause);
  }

  public SerializableException(String message) {
        super(message);
    }
}
