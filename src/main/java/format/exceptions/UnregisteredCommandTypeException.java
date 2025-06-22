package format.exceptions;

public class UnregisteredCommandTypeException extends RuntimeException {
    public UnregisteredCommandTypeException(int commandType) {
        super("Unregistered command type: " + commandType);
    }
}
