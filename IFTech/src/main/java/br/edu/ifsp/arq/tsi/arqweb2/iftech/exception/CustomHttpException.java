package br.edu.ifsp.arq.tsi.arqweb2.iftech.exception;

public class CustomHttpException extends RuntimeException {
    private final int statusCode;
    private final String message;

    public CustomHttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessageBody() {
        return message;
    }
}
