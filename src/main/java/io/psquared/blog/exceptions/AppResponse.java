package io.psquared.blog.exceptions;

import java.time.LocalDateTime;

public class AppResponse {
    private String message;
    private LocalDateTime timestamp;
    private boolean error;
    private Object errors;

    public AppResponse(String message, LocalDateTime timestamp, boolean error, Object payload) {
        this.message = message;
        this.timestamp = timestamp;
        this.error = error;
        this.errors = payload;
    }

    public AppResponse(String message, LocalDateTime timestamp, boolean error) {
        this.message = message;
        this.timestamp = timestamp;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }
}
