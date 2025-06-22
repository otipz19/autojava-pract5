package encoding;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static encoding.BuildSamplePackageUtil.buildSamplePackageFromMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.HexString.bytesToHex;

public class CsvSourceParameterizedPackageEncoderTest {
    @ParameterizedTest
    @CsvSource({
            "'Hello, World!', 13010000000000000002000000155DA8000000030000000448656C6C6F2C20576F726C64217AB8",
            "'Bye, World!', 13010000000000000002000000135F2800000003000000044279652C20576F726C6421CD61",
            "I am a cat, 13010000000000000002000000129FE900000003000000044920616D206120636174E80E",
            "Meow, 130100000000000000020000000C976900000003000000044D656F77958F",
            "'', 130100000000000000020000000854680000000300000004C345",
            "привіт, 13010000000000000002000000149D690000000300000004D0BFD180D0B8D0B2D196D1824A75"
    })
    public void encode_givenPackage_shouldEncodeToHexString(String inputMsg, String expectedHex) {
        Package inputPkg = buildSamplePackageFromMessage(inputMsg);
        byte[] encoded = PackageEncoder.encode(inputPkg);
        String actualHex = bytesToHex(encoded);
        assertEquals(expectedHex, actualHex);
    }
}
