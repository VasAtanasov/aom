package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Filter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilterRepositoryCustom {
  List<Filter> findAllByUserId(UUID userId);

  Optional<Filter> findFilterById(UUID filterId);
}
