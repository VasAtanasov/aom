package bg.autohouse.data.models;

import java.time.LocalDate;
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
  public static final String PROVINCES = PREFIX + "provinces";
  public static final String ADDRESSES = PREFIX + "addresses";
  public static final String OFFERS = PREFIX + "offers";
  public static final String VEHICLES = PREFIX + "vehicles";
  public static final String USERS = PREFIX + "users";
  public static final String DEALERSHIPS = PREFIX + "dealerships";
  public static final String TRIMS = PREFIX + "trims";
  public static final String TOKENS = PREFIX + "tokens";

  public static final Integer MIN_VALUE = 1;
  public static final Integer PRICE_TO = 10_000_000;
  public static final Integer MILEAGE_TO = 10_000_000;
  public static final Integer DOORS_TO = 7;
  public static final Integer SEATS_TO = 12;
  public static final Integer POWER_TO = 5000;

  public static final Integer YEAR_FROM = 1930;
  public static final Integer YEAR_TO = LocalDate.now().getYear();
}
