package ru.yandex.practicum.filmorate.exception;

public class NoFoundDataException extends RuntimeException {
    public NoFoundDataException(String message) {
        super(message);
    }
}
