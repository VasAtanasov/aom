package bg.autohouse.data.projections;

import java.util.List;

public interface ModelProjection {
  Long getId();

  List<YearsOnly> getTrims();
}
