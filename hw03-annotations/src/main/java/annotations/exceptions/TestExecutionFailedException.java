package annotations.exceptions;

public class TestExecutionFailedException extends RuntimeException {
    public TestExecutionFailedException(String message) {
        super(message);
    }
}