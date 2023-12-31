package exceptions;

public class AutomationUtilException extends RuntimeException {

    public AutomationUtilException(String message) {
        super(message);
    }

    public AutomationUtilException(String message, Throwable cause) {
        super(message, cause);
    }
}