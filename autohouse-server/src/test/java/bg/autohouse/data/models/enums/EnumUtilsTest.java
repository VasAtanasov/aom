package bg.autohouse.data.models.enums;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EnumUtilsTest {

    @Test
    public void whenEnumMap_onEnumClass_shouldWorkCorrect() {
        List<String> enumList = EnumUtils.ENUM_MAP(BodyStyle.class)
                .values()
                .stream()
                .map(BodyStyle::toString)
                .collect(Collectors.toUnmodifiableList());

        boolean enumStrings = Stream.of(BodyStyle.values())
                .map(Enum::toString)
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toUnmodifiableList())
                .containsAll(enumList);

        assertThat(enumStrings, is(true));
    }

}