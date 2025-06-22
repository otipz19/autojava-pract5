package format.exceptions;

public class UnregisteredMessageTypeException extends RuntimeException {
    public UnregisteredMessageTypeException(Class<?> msgType) {
        super("Unregistered message type: " + msgType);
    }
}
