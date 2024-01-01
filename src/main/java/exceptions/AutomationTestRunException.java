package exceptions;

public class AutomationTestRunException extends RuntimeException {

    public AutomationTestRunException(String message) {
        super(message);
    }

    public AutomationTestRunException(String message, Throwable throwable) {
        super(message, throwable);
    }
}