package bg.autohouse.utils;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.util.StringGenericUtils;
import org.junit.jupiter.api.Test;

public class StringGenericUtilsTest {

  @Test
  void when_nextString_whitValidLength_shouldReturn() {
    String randomString = StringGenericUtils.nextString(10);
    String randomUsername = StringGenericUtils.generateRandomDisplayName();

    assertThat(randomString.length()).isEqualTo(10);
    assertThat(randomUsername.length()).isEqualTo(14);
  }
}
