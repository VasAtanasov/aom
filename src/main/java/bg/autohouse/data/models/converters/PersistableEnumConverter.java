package bg.autohouse.data.models.converters;

import bg.autohouse.data.models.PersistableEnum;
import bg.autohouse.util.ClassUtils;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;

public interface PersistableEnumConverter<E extends PersistableEnum>
    extends AttributeConverter<E, String> {

  @SuppressWarnings("unchecked")
  default Class<E> getEnumClass() {
    return (Class<E>)
        ClassUtils.getTypeArgumentOfSuperClass(this, PersistableEnumConverter.class, 0);
  }

  @Override
  default String convertToDatabaseColumn(final E enumValue) {
    return enumValue == null ? null : enumValue.getDatabaseValue();
  }

  @Override
  default E convertToEntityAttribute(final String dbData) {
    return dbData == null
        ? null
        : Stream.of(getEnumClass().getEnumConstants())
            .filter(value -> value.getDatabaseValue().equals(dbData))
            .findFirst()
            .orElse(null);
  }
}
