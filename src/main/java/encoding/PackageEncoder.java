package encoding;

import encoding.exceptions.HeaderCRCValidationException;
import encoding.exceptions.MagicByteValidationException;
import encoding.exceptions.PayloadCRCValidationException;
import utils.CRC16;
import encoding.structure.PackageStructure;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class PackageEncoder {
    public static final byte B_MAGIC = 0x13;

    public static byte[] encode(Package pkg) {
        byte[] msgBytes = pkg.message().getBytes(StandardCharsets.UTF_8);
        PackageStructure packageStructure = new PackageStructure(msgBytes.length);

        ByteBuffer buffer = ByteBuffer.allocate(packageStructure.totalLength).order(ByteOrder.BIG_ENDIAN);
        buffer.put(B_MAGIC)
                .put(pkg.bSrc())
                .putLong(pkg.bPktId())
                .putInt(packageStructure.payloadLength)
                .putShort(CRC16.calcCrc(buffer.array(), 0, packageStructure.headerLength))
                .putInt(pkg.cType())
                .putInt(pkg.bUserId())
                .put(msgBytes)
                .putShort(
                        CRC16.calcCrc(
                                buffer.array(),
                                packageStructure.cType.offset(),
                                packageStructure.payloadLength
                        )
                );

        return buffer.array();
    }

    public static Package decode(byte[] pkgBytes) {
        ByteBuffer buffer = ByteBuffer.wrap(pkgBytes);

        byte bMagic = buffer.get();
        if (bMagic != B_MAGIC) {
            throw new MagicByteValidationException();
        }

        byte bSrc = buffer.get();
        long bPktId = buffer.getLong();
        int wLen = buffer.getInt();
        PackageStructure packageStructure = PackageStructure.fromPayloadLength(wLen);

        short wCrc16Header = buffer.getShort();
        short expectedWCrc16Header = CRC16.calcCrc(buffer.array(), 0, packageStructure.headerLength);
        if(wCrc16Header != expectedWCrc16Header) {
            throw new HeaderCRCValidationException();
        }

        int cType = buffer.getInt();
        int bUserId = buffer.getInt();
        byte[] msgBytes = new byte[packageStructure.message.length()];
        buffer.get(msgBytes, 0, packageStructure.message.length());

        short wCrc16Payload = buffer.getShort();
        short expectedWCrc16Payload = CRC16.calcCrc(
                buffer.array(),
                packageStructure.cType.offset(),
                packageStructure.payloadLength
        );
        if(wCrc16Payload != expectedWCrc16Payload) {
            throw new PayloadCRCValidationException();
        }

        String msg = new String(msgBytes);
        return new Package(bSrc, bPktId, cType, bUserId, msg);
    }
}
