package bg.autohouse.data.repositories;

import bg.autohouse.data.models.offer.Offer;
import bg.autohouse.data.projections.offer.CountStatistics;
import bg.autohouse.data.projections.offer.Statistics;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OfferRepository
    extends JpaRepository<Offer, UUID>, JpaSpecificationExecutor<Offer> {

  @Query(
      value =
          "SELECT o "
              + "FROM Offer o "
              + "JOIN FETCH o.vehicle v "
              + "JOIN FETCH o.location lo "
              + "WHERE o.isActive = 1")
  Stream<Offer> findLatestOffers(Pageable pageable);

  @Query(
      value =
          "SELECT o "
              + "FROM Offer o "
              + "JOIN FETCH o.vehicle v "
              + "JOIN FETCH o.location lo "
              + "WHERE o.isActive = 1")
  List<Offer> findAll();

  @Query(
      value =
          "SELECT o "
              + "FROM Offer o "
              + "JOIN FETCH o.vehicle v "
              + "JOIN FETCH o.location lo "
              + "WHERE o.isActive = 1",
      countQuery =
          "SELECT count(o) "
              + "FROM Offer o "
              + "LEFT JOIN o.vehicle v "
              + "LEFT JOIN o.location lo "
              + "WHERE o.isActive = 1")
  Page<Offer> findLatestOffersPage(Pageable pageable);

  @Query(
      value =
          "SELECT DISTINCT v.maker_name "
              + "FROM auto_offers as o "
              + "LEFT JOIN auto_vehicles as v on o.id = v.offer_id "
              + "ORDER BY t.maker_name asc",
      nativeQuery = true)
  Stream<Object[]> findAllMakersNames();;

  @Query(
      value =
          "SELECT "
              + "MAX(o.price) as maxPrice, "
              + "MIN(o.price) as minPrice, "
              + "MAX(v.year) as maxYear, "
              + "MIN(v.year) as minYear, "
              + "MAX(v.mileage) as maxMileage, "
              + "MIN(v.mileage) as minMileage, "
              + "COUNT(*) as totalOffers "
              + "FROM auto_offers as o "
              + "LEFT JOIN auto_vehicles as v on o.id = v.offer_id "
              + "WHERE o.is_active = 1",
      nativeQuery = true)
  Statistics getStatistics();

  @Query(
      value =
          "SELECT v.bodyStyle as bodyStyle, COUNT(o) as offersCount "
              + "FROM Offer as o "
              + "LEFT JOIN o.vehicle v "
              + "GROUP BY v.bodyStyle "
              + "ORDER BY v.bodyStyle")
  List<CountStatistics> getCountStatistics();

  @Query(
      value =
          "SELECT DISTINCT o "
              + "FROM Offer o "
              + "LEFT JOIN FETCH o.vehicle v "
              + "LEFT JOIN FETCH o.location lo "
              + "LEFT JOIN FETCH o.account acc "
              + "LEFT JOIN FETCH acc.user usr "
              + "LEFT JOIN FETCH acc.address adr "
              + "LEFT JOIN FETCH adr.location loc "
              + "WHERE o.isActive = 1 AND o.id = :id")
  Optional<Offer> findOfferById(UUID id);

  @Query(
      value =
          "SELECT o "
              + "FROM Offer o "
              + "LEFT JOIN FETCH o.vehicle v "
              + "LEFT JOIN FETCH v.features f "
              + "LEFT JOIN FETCH o.location lo "
              + "LEFT JOIN FETCH o.account acc "
              + "LEFT JOIN FETCH acc.user usr "
              + "LEFT JOIN FETCH acc.address adr "
              + "LEFT JOIN FETCH adr.location loc "
              + "WHERE o.isActive = 1 AND o.id = :id AND usr.id = :creatorId")
  Optional<Offer> findOneByIdAndCreatorId(UUID id, UUID creatorId);

  @Query(
      value =
          "SELECT DISTINCT * "
              + "FROM "
              + "auto_offers AS ao "
              + "INNER JOIN "
              + "auto_vehicles AS av ON ao.id = av.offer_id "
              + "INNER JOIN "
              + "auto_vehicles_features AS avf ON av.id = avf.vehicle_id "
              + "INNER JOIN "
              + "auto_locations AS al ON ao.location_id = al.id "
              + "INNER JOIN "
              + "auto_filter_features AS aff ON avf.feature = aff.feature "
              + "WHERE "
              + "aff.filter_id = :filterId "
              + "GROUP BY av.id "
              + "HAVING COUNT(DISTINCT avf.feature) >= (SELECT DISTINCT COUNT(*) "
              + "FROM "
              + "auto_filter_features AS aff2 "
              + "WHERE "
              + "aff2.filter_id = :filterId) ",
      nativeQuery = true)
  List<Offer> searchOffersIdsWithFeatures(@Param("filterId") UUID filterId);

  @Query(
      value =
          "SELECT DISTINCT o "
              + "FROM Offer o "
              + "LEFT JOIN FETCH o.vehicle v "
              + "LEFT JOIN FETCH o.location lo "
              + "LEFT JOIN FETCH o.account acc "
              + "LEFT JOIN FETCH acc.user usr "
              + "LEFT JOIN FETCH acc.address adr "
              + "LEFT JOIN FETCH adr.location loc "
              + "WHERE o.isActive = 1 AND acc.id = :accountId",
      countQuery =
          "SELECT DISTINCT count(o) "
              + "FROM Offer o "
              + "LEFT JOIN o.vehicle v "
              + "LEFT JOIN o.location lo "
              + "LEFT JOIN o.account acc "
              + "LEFT JOIN acc.user usr "
              + "LEFT JOIN acc.address adr "
              + "LEFT JOIN adr.location loc "
              + "WHERE o.isActive = 1 AND acc.id = :accountId")
  Page<Offer> findAllByAccountId(UUID accountId, Pageable pageable);

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("UPDATE Offer o SET o.isActive = 0 WHERE o.updatedAt <= :before")
  int setInactiveOffersBefore(Date before);

  long countByAccountId(UUID accountId);
}
