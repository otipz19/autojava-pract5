package encoding;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static encoding.BuildSamplePackageUtil.buildSamplePackageFromMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.HexString.bytesToHex;

@Tag("parameterized")
public class MethodSourceParameterizedPackageEncoderTest {
    @ParameterizedTest
    @MethodSource("encoding.MethodSourceParameterizedPackageEncoderTest#provideArguments_for_encode_givenPackage_shouldEncodeToHexString")
    public void encode_givenPackage_shouldEncodeToHexString(Package inputPkg, String expectedHex) {
        byte[] encoded = PackageEncoder.encode(inputPkg);
        String actualHex = bytesToHex(encoded);
        assertEquals(expectedHex, actualHex);
    }

    public static Stream<Arguments> provideArguments_for_encode_givenPackage_shouldEncodeToHexString() {
        return Stream.of(
                Arguments.of(
                        buildSamplePackageFromMessage("Hello, World!"),
                        "13010000000000000002000000155DA8000000030000000448656C6C6F2C20576F726C64217AB8"
                ),
                Arguments.of(
                        buildSamplePackageFromMessage("Bye, World!"),
                        "13010000000000000002000000135F2800000003000000044279652C20576F726C6421CD61"
                ),
                Arguments.of(
                        buildSamplePackageFromMessage("I am a cat"),
                        "13010000000000000002000000129FE900000003000000044920616D206120636174E80E"
                ),
                Arguments.of(
                        buildSamplePackageFromMessage("Meow"),
                        "130100000000000000020000000C976900000003000000044D656F77958F"
                ),
                Arguments.of(
                        buildSamplePackageFromMessage(""),
                        "130100000000000000020000000854680000000300000004C345"
                ),
                Arguments.of(
                        buildSamplePackageFromMessage("привіт"),
                        "13010000000000000002000000149D690000000300000004D0BFD180D0B8D0B2D196D1824A75"
                )
        );
    }
}
