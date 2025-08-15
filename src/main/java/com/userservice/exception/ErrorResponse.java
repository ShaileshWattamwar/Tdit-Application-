package com.userservice.exception;



import java.util.List;

public class ErrorResponse {
    //pr
    private String message;
    private List<FieldError> error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldError> getError() {
        return error;
    }

    public void setError(List<FieldError> error) {
        this.error = error;
    }

    public ErrorResponse(String message, List<FieldError> error) {
        this.message = message;
        this.error = error;
    }

    public static class FieldError{
        private String field;
        private String message;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
