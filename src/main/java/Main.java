import crypto.Crypto;
import format.FormattedPackage;
import messages.DeleteProduct;
import utils.HexString;

import java.security.Key;

public class Main {
    public static void main(String[] args) {
        Key key = Crypto.generateKey();
        var msg = new DeleteProduct(1);
        var formattedEgress = new FormattedPackage<>((byte) 1, 2, 3, msg);
        System.out.println("Formatted egress package: " + formattedEgress);
        byte[] egress = PackageConverter.convertToEgress(formattedEgress, key);
        System.out.println("Hex string egress package: " + HexString.bytesToHex(egress));
        var convertedFromIngress = PackageConverter.convertFromIngress(egress, key, DeleteProduct.class);
        System.out.println("Formatted ingress package: " + convertedFromIngress);
    }
}
