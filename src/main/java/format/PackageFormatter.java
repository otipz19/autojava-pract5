package format;

import com.fasterxml.jackson.databind.ObjectMapper;
import encoding.Package;
import format.exceptions.InvalidMessageTypeException;
import format.exceptions.UnregisteredCommandTypeException;
import format.exceptions.UnregisteredMessageTypeException;
import lombok.SneakyThrows;
import messages.DeleteProduct;
import messages.UpsertProduct;

import java.util.HashMap;
import java.util.Map;

public class PackageFormatter {
    private static final Map<Integer, Class<?>> COMMAND_TYPE_TO_MSG_TYPE = new HashMap<>();
    private static final Map<Class<?>, Integer> MSG_TYPE_TO_COMMAND_TYPE = new HashMap<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        COMMAND_TYPE_TO_MSG_TYPE.put(1, UpsertProduct.class);
        COMMAND_TYPE_TO_MSG_TYPE.put(2, DeleteProduct.class);

        for (var entry : COMMAND_TYPE_TO_MSG_TYPE.entrySet()) {
            MSG_TYPE_TO_COMMAND_TYPE.put(entry.getValue(), entry.getKey());
        }
    }

    @SneakyThrows
    public static <TMessage> FormattedPackage<TMessage> getFormatted(Package pkg, Class<TMessage> expectedMsgType) {
        int commandType = pkg.cType();
        Class<?> actualMsgType = getMsgType(commandType);
        if (!actualMsgType.equals(expectedMsgType)) {
            throw new InvalidMessageTypeException();
        }
        TMessage msg = OBJECT_MAPPER.readValue(pkg.message(), expectedMsgType);
        return new FormattedPackage<>(pkg.bSrc(), pkg.bPktId(), pkg.bUserId(), msg);
    }

    @SneakyThrows
    public static <TMessage> Package getRaw(FormattedPackage<TMessage> formattedPkg) {
        String rawMsg = OBJECT_MAPPER.writeValueAsString(formattedPkg.message());
        int commandType = getCommandType(formattedPkg.message().getClass());
        return new Package(formattedPkg.bSrc(), formattedPkg.bPktId(), commandType, formattedPkg.bUserId(), rawMsg);
    }

    public static <TMessage> int getCommandType(Class<TMessage> msgType) {
        var result = MSG_TYPE_TO_COMMAND_TYPE.get(msgType);
        if (result == null) {
            throw new UnregisteredMessageTypeException(msgType);
        }
        return result;
    }

    private static Class<?> getMsgType(int commandType) {
        var result = COMMAND_TYPE_TO_MSG_TYPE.get(commandType);
        if (result == null) {
            throw new UnregisteredCommandTypeException(commandType);
        }
        return result;
    }
}
