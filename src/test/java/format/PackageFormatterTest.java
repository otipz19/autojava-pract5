package format;

import encoding.Package;
import format.exceptions.UnregisteredCommandTypeException;
import format.exceptions.UnregisteredMessageTypeException;
import messages.DeleteProduct;
import messages.UpsertProduct;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackageFormatterTest {
    @Test
    public void getCommandType_givenRegisteredMessageType_returnsCommandType() {
        assertDoesNotThrow(() -> PackageFormatter.getCommandType(UpsertProduct.class));
    }

    @Test
    public void getCommandType_givenNotRegisteredMessageType_throws() {
        assertThrows(UnregisteredMessageTypeException.class, () -> PackageFormatter.getCommandType(Integer.class));
    }

    @Test
    public void givenRegisteredMessageType_formatsPackage() {
        DeleteProduct deleteProduct = new DeleteProduct(1);
        int expectedCommandType = PackageFormatter.getCommandType(DeleteProduct.class);
        var expectedFormatted = new FormattedPackage<>((byte) 1, 2, 3, deleteProduct);

        Package rawPkg = PackageFormatter.getRaw(expectedFormatted);
        assertEquals(expectedCommandType, rawPkg.cType());

        var actualFormatted = PackageFormatter.getFormatted(rawPkg, DeleteProduct.class);

        assertEquals(expectedFormatted, actualFormatted);
    }

    @Test
    public void givenNotRegisteredCommandType_throws() {
        Package invalidRawPkg = new Package((byte) 1, 2, Integer.MIN_VALUE, 4, "invalid");
        assertThrows(UnregisteredCommandTypeException.class, () -> PackageFormatter.getFormatted(invalidRawPkg, String.class));
    }
}