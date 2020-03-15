package bg.autohouse.data.models;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EntityConstants {

  public static final String SCHEMA = "autohouse_db";

  public static final String PREFIX = "auto_";

  public static final String MAKERS = PREFIX + "makers";
  public static final String MODELS = PREFIX + "models";
  public static final String ENGINES = PREFIX + "engines";
  public static final String FILTERS = PREFIX + "filters";
  public static final String IMAGES = PREFIX + "images";
  public static final String LOCATIONS = PREFIX + "locations";
  public static final String OFFERS = PREFIX + "offers";
  public static final String VEHICLES = PREFIX + "vehicles";
  public static final String USERS = PREFIX + "users";
}
