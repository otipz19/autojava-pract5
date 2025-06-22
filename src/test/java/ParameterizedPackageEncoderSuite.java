import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("encoding")
@IncludeTags("parameterized")
public class ParameterizedPackageEncoderSuite {
}
