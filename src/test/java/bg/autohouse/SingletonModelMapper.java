package bg.autohouse;

import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;

@UtilityClass
public class SingletonModelMapper {

  public static ModelMapper mapper() {
    ModelMapper mapper = new ModelMapper();
    mapper
        .getConfiguration()
        .setAmbiguityIgnored(true)
        .setFieldAccessLevel(AccessLevel.PRIVATE)
        .setFieldMatchingEnabled(true);
    return mapper;
  }
}
