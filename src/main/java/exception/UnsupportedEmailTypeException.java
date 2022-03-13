package exception;

import model.EmailType;

import java.util.Arrays;

public class UnsupportedEmailTypeException extends RuntimeException {
    public UnsupportedEmailTypeException() {
        super("Unsupported email type provided, use any defined from " + Arrays.toString(EmailType.values()));
    }
}
