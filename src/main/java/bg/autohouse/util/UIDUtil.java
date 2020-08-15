package bg.autohouse.util;

import java.nio.ByteBuffer;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UIDUtil {

  public static String generateStringUid() {
    return UUID.randomUUID().toString();
  }

  public static UUID generateUID() {
    return UUID.randomUUID();
  }

  public static UUID convert(String source) {
    return (Assert.has(source) ? UUID.fromString(source.trim()) : null);
  }

  public static byte[] getUidBytes() {
    return getBytesFromUUID(UUID.randomUUID());
  }

  public static byte[] getBytesFromUUID(UUID uuid) {
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }

  public static UUID getUUIDFromBytes(byte[] bytes) {
    ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
    long high = byteBuffer.getLong();
    long low = byteBuffer.getLong();
    return new UUID(high, low);
  }
}
