package cucumber.runtime;

public class PassVerdictException  extends RuntimeException {
    public PassVerdictException() {
    }

    public PassVerdictException(String message) {
        super(message);
    }

    public PassVerdictException(String message, Throwable e) {
        super(message, e);
    }

    public PassVerdictException(Throwable e) {
        super(e);
    }
}

