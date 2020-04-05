package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Offer;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository
    extends JpaRepository<Offer, String>, JpaSpecificationExecutor<Offer> {
  // TODO to format queries for new location entity
  @Query(
      value =
          "SELECT o FROM Offer o "
              + "JOIN FETCH o.vehicle v "
              + "JOIN FETCH v.engine e "
              + "JOIN FETCH v.maker mk "
              + "JOIN FETCH v.model m "
              + "WHERE o.isActive = 1 AND o.isDeleted = 0 AND o.isExpired = 0")
  Stream<Offer> findLatestOffers(Pageable pageable);

  @Query(
      value =
          "SELECT o FROM Offer o "
              + "JOIN FETCH o.vehicle v "
              + "JOIN FETCH v.engine e "
              + "JOIN FETCH v.maker mk "
              + "JOIN FETCH v.model m "
              + "WHERE o.isActive = 1 AND o.isDeleted = 0 AND o.isExpired = 0")
  List<Offer> findAll();

  @Query(
      value =
          "SELECT o FROM Offer o "
              + "JOIN FETCH o.vehicle v "
              + "JOIN FETCH v.engine e "
              + "JOIN FETCH v.maker mk "
              + "JOIN FETCH v.model m "
              + "WHERE o.isActive = 1 AND o.isDeleted = 0 AND o.isExpired = 0",
      countQuery =
          "SELECT count(o) FROM Offer o "
              + "LEFT JOIN o.vehicle v "
              + "LEFT JOIN v.engine e "
              + "LEFT JOIN v.maker mk "
              + "LEFT JOIN v.model m "
              + "WHERE o.isActive = 1 AND o.isDeleted = 0 AND o.isExpired = 0")
  Page<Offer> findLatestOffersPage(Pageable pageable);
}
