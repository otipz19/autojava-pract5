import crypto.Crypto;
import format.FormattedPackage;
import format.exceptions.InvalidMessageTypeException;
import messages.DeleteProduct;
import messages.UpsertProduct;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class PackageConverterTest {
    @Test
    public void givenFormattedPackage_convertsToEgress_thenConvertsBack() {
        var expectedPkg = new FormattedPackage<>((byte) 1, 2, 3, new DeleteProduct(1));
        Key key = Crypto.generateKey();

        byte[] egress = PackageConverter.convertToEgress(expectedPkg, key);
        var actualPkg = PackageConverter.convertFromIngress(egress, key, DeleteProduct.class);

        assertEquals(expectedPkg, actualPkg);
    }

    @Test
    public void givenMismatchedMessageType_throws() {
        var expectedPkg = new FormattedPackage<>((byte) 1, 2, 3, new DeleteProduct(1));
        Key key = Crypto.generateKey();

        byte[] egress = PackageConverter.convertToEgress(expectedPkg, key);
        assertThrows(InvalidMessageTypeException.class,
                () -> PackageConverter.convertFromIngress(egress, key, UpsertProduct.class));
    }
}