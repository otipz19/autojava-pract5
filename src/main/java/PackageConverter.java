import crypto.Crypto;
import encoding.Package;
import encoding.PackageEncoder;
import format.FormattedPackage;
import format.PackageFormatter;

import java.security.Key;

public class PackageConverter {
    public static <TMessage> byte[] convertToEgress(FormattedPackage<TMessage> formattedPackage, Key key) {
        Package rawPkg = PackageFormatter.getRaw(formattedPackage);
        byte[] encodedPkg = PackageEncoder.encode(rawPkg);
        return Crypto.encrypt(encodedPkg, key);
    }

    public static <TMessage> FormattedPackage<TMessage> convertFromIngress(byte[] encryptedPkg, Key key, Class<TMessage> messageType) {
        byte[] decryptedPkg = Crypto.decrypt(encryptedPkg, key);
        Package rawPkg = PackageEncoder.decode(decryptedPkg);
        return PackageFormatter.getFormatted(rawPkg, messageType);
    }
}
