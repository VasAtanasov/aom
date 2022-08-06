package bg.autohouse.spider.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class IOUtil {
  public static InputStream toInputStream(final String input, final Charset charset) {
    return new ByteArrayInputStream(input.getBytes(charset));
  }

  public static InputStream toInputStream(final String input) {
    return IOUtil.toInputStream(input, StandardCharsets.UTF_8);
  }

  public static String toString(final InputStream input) {
    try {
      return IOUtils.toString(input, StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
