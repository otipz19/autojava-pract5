package encoding;

public class BuildSamplePackageUtil {
    public static Package buildSamplePackageFromMessage(String msg) {
        return new Package((byte) 1, 2, 3, 4, msg);
    }
}
