package bg.autohouse.data.repositories;

import bg.autohouse.data.models.offer.Offer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository
    extends JpaRepository<Offer, UUID>, JpaSpecificationExecutor<Offer> {
  // TODO to format queries for new location entity
  @Query(
      value =
          "SELECT o FROM Offer o "
              + "JOIN FETCH o.vehicle v JOIN FETCH v.engine e "
              + "WHERE o.isActive = 1")
  Stream<Offer> findLatestOffers(Pageable pageable);

  @Query(
      value =
          "SELECT o FROM Offer o "
              + "JOIN FETCH o.vehicle v JOIN FETCH v.engine e "
              + "WHERE o.isActive = 1")
  List<Offer> findAll();

  @Query(
      value =
          "SELECT o FROM Offer o "
              + "JOIN FETCH o.vehicle v JOIN FETCH v.engine e "
              + "WHERE o.isActive = 1",
      countQuery =
          "SELECT count(o) FROM Offer o "
              + "LEFT JOIN o.vehicle v LEFT JOIN v.engine e "
              + "WHERE o.isActive = 1")
  Page<Offer> findLatestOffersPage(Pageable pageable);
}
