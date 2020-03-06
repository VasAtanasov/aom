package bg.autohouse.util;

import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringGenericUtils {

  private static final char[] symbols;
  private static final char[] specials;
  private static final Random random;

  static {
    final StringBuilder tmp = new StringBuilder();
    specials = "!@#".toCharArray();
    tmp.append(specials);
    for (char ch = '1'; ch <= '9'; ++ch) {
      tmp.append(ch);
    }
    for (char ch = 'a'; ch <= 'z'; ++ch) {
      if (ch != 'o') {
        tmp.append(ch);
      }
    }
    for (char ch = 'A'; ch <= 'Z'; ++ch) {
      if (ch != 'O') {
        tmp.append(ch);
      }
    }
    symbols = tmp.toString().toCharArray();
    random = new Random();
  }

  public static String nextString(final int length) {
    if (length < 1) {
      throw new IllegalArgumentException("length < 1: " + length);
    }
    final char[] buf = new char[length];

    for (int idx = 0; idx < buf.length; ++idx) {
      buf[idx] = symbols[random.nextInt(symbols.length)];
    }

    return new String(buf);
  }

  public static String nextPassword(final int length) {
    if (length < 1) {
      throw new IllegalArgumentException("length < 1: " + length);
    }
    final char[] buf = new char[length];
    while (!verifyGoodPassword(buf)) {
      for (int idx = 0; idx < buf.length; ++idx) {
        final int nextInt = random.nextInt(symbols.length);
        buf[idx] = symbols[nextInt];
      }
    }
    return String.valueOf(buf);
  }

  private static boolean verifyGoodPassword(final char[] buf) {
    boolean hasLower = false;
    boolean hasUpper = false;
    boolean hasNumber = false;
    boolean hasSpecial = false;
    for (final char c : buf) {
      if (Character.isDigit(c)) {
        hasNumber = true;
      } else if (Character.isUpperCase(c)) {
        hasUpper = true;
      } else if (Character.isLowerCase(c)) {
        hasLower = true;
      } else if (specials.toString().contains("" + c)) {
        hasSpecial = true;
      }
    }
    return hasLower && hasUpper && hasNumber && hasSpecial;
  }
}