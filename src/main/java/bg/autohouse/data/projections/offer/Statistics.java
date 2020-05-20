package bg.autohouse.data.projections.offer;

public interface Statistics {
  Long getTotalOffers();

  Long getMaxPrice();

  Long getMinPrice();

  Long getMinYear();

  Long getMaxYear();

  Long getMaxMileage();

  Long getMinMileage();
}
