package crypto;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Crypto {
    private static final Cipher CIPHER;
    private static final KeyGenerator KEY_GENERATOR;

    static {
        try {
            CIPHER = Cipher.getInstance("AES");
            KEY_GENERATOR = KeyGenerator.getInstance("AES");
            KEY_GENERATOR.init(128);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public static byte[] encrypt(byte[] plainText, Key key) {
        CIPHER.init(Cipher.ENCRYPT_MODE, key);
        return CIPHER.doFinal(plainText);
    }

    @SneakyThrows
    public static byte[] decrypt(byte[] cipherText, Key key) {
        CIPHER.init(Cipher.DECRYPT_MODE, key);
        return CIPHER.doFinal(cipherText);
    }

    @SneakyThrows
    public static Key generateKey() {
        return KEY_GENERATOR.generateKey();
    }
}
