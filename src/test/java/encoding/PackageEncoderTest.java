package encoding;

import encoding.exceptions.HeaderCRCValidationException;
import encoding.exceptions.MagicByteValidationException;
import encoding.exceptions.PayloadCRCValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utils.HexString.bytesToHex;

class PackageEncoderTest {
    @Test
    public void encode_givenPackage_shouldEncodeToHexString() {
        Package pkg = new Package((byte)1, 2, 3, 4, "Hello, World!");

        String expectedHex = "13010000000000000002000000155DA8000000030000000448656C6C6F2C20576F726C64217AB8";

        byte[] encoded = PackageEncoder.encode(pkg);
        String hex = bytesToHex(encoded);
        assertEquals(expectedHex, hex);
    }

    @Test
    public void encode_givenEmptyMessage_shouldEncodeToHexString() {
        Package pkg = new Package((byte)1, 2, 3, 4, "");

        String expectedHex = "130100000000000000020000000854680000000300000004C345";

        byte[] encoded = PackageEncoder.encode(pkg);
        String hex = bytesToHex(encoded);
        assertEquals(expectedHex, hex);
    }

    @Test
    public void decode_givenEncodedPkg_shouldDecode() {
        Package pkg = new Package((byte)1, 2, 3, 4, "Hello, World!");
        byte[] encoded = PackageEncoder.encode(pkg);

        Package decoded = PackageEncoder.decode(encoded);
        assertEquals(pkg, decoded);
    }

    @Test
    public void decode_givenWrongMagicByte_shouldThrow() {
        Package pkg = new Package((byte)1, 2, 3, 4, "Hello, World!");
        byte[] encoded = PackageEncoder.encode(pkg);
        encoded[0] = PackageEncoder.B_MAGIC - 1;

        assertThrows(MagicByteValidationException.class, () -> PackageEncoder.decode(encoded));
    }

    @Test
    public void decode_givenChangedHeader_shouldThrow() {
        Package pkg = new Package((byte)1, 2, 3, 4, "Hello, World!");
        byte[] encoded = PackageEncoder.encode(pkg);
        encoded[1] = (byte)(encoded[1] + 1);

        assertThrows(HeaderCRCValidationException.class, () -> PackageEncoder.decode(encoded));
    }

    @Test
    public void decode_givenChangedPayload_shouldThrow() {
        Package pkg = new Package((byte)1, 2, 3, 4, "Hello, World!");
        byte[] encoded = PackageEncoder.encode(pkg);
        encoded[16] = (byte)(encoded[16] + 1);

        assertThrows(PayloadCRCValidationException.class, () -> PackageEncoder.decode(encoded));
    }
}