package crypto;

import encoding.PackageEncoder;
import org.junit.jupiter.api.Test;
import encoding.Package;
import static org.junit.jupiter.api.Assumptions.*;
import utils.AppConfig;
import utils.Environment;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class CryptoTest {
    @Test
    public void givenPlainText_createsCipherText_thenRetrievesPlainTextBack() {
        assumeTrue(AppConfig.CURRENT_ENV == Environment.STAGE);

        Package pkg = new Package((byte)1, 2, 3, 4, "Hello, World!");
        byte[] plainText = PackageEncoder.encode(pkg);
        Key key = Crypto.generateKey();

        byte[] cipherText = Crypto.encrypt(plainText, key);
        byte[] decrypted = Crypto.decrypt(cipherText, key);

        Package decodedPkg = PackageEncoder.decode(decrypted);

        assertEquals(pkg, decodedPkg);
    }
}