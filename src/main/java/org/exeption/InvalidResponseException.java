package org.exeption;

public class InvalidResponseException extends Exception {
    private String message;

    public InvalidResponseException(String responseCode, String message) {
        super(responseCode + " : " + message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
