package encoding.structure;

public class PackageStructure {
    public final PackageStructurePart bMagic = new PackageStructurePart(0, 1);
    public final PackageStructurePart bSrc = new PackageStructurePart(1, 1);
    public final PackageStructurePart bPktId = new PackageStructurePart(2, 8);
    public final PackageStructurePart wLen = new PackageStructurePart(10, 4);
    public final PackageStructurePart wCrc16Header = new PackageStructurePart(14, 2);
    public final PackageStructurePart cType = new PackageStructurePart(16, 4);
    public final PackageStructurePart bUserId = new PackageStructurePart(20, 4);
    public final PackageStructurePart message;
    public final PackageStructurePart wCrc16Payload;

    public final int headerLength;
    public final int payloadLength;
    public final int totalLength;

    public PackageStructure(int messageLength) {
        message = new PackageStructurePart(24, messageLength);
        wCrc16Payload = new PackageStructurePart(24 + messageLength, 2);
        headerLength = bMagic.length() + bSrc.length() + bPktId.length() + wLen.length();
        payloadLength = cType.length() + bUserId.length() + message.length();
        totalLength = headerLength + wCrc16Header.length() + payloadLength + wCrc16Payload.length();
    }

    public static PackageStructure fromPayloadLength(int payloadLength) {
        return new PackageStructure(payloadLength - 8);
    }
}
