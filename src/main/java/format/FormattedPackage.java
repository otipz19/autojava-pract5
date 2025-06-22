package format;

public record FormattedPackage<TMessage>(byte bSrc, long bPktId, int bUserId, TMessage message) {

}
