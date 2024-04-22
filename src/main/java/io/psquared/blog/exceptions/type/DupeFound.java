package io.psquared.blog.exceptions.type;

public class DupeFound extends RuntimeException{
    public DupeFound(String message) {
        super(message);
    }
}
