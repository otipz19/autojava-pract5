import encoding.CsvSourceParameterizedPackageEncoderTest;
import encoding.MethodSourceParameterizedPackageEncoderTest;
import encoding.PackageEncoderTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        PackageEncoderTest.class,
        CsvSourceParameterizedPackageEncoderTest.class,
        MethodSourceParameterizedPackageEncoderTest.class
})
public class PackageEncoderSuite {
}
