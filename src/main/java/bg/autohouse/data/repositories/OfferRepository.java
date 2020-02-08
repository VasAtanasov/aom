package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    @Query(value = "SELECT DISTINCT o FROM Offer o " +
            "JOIN FETCH o.vehicle v " +
            "JOIN FETCH v.engine e " +
            "JOIN FETCH v.maker mk " +
            "JOIN FETCH v.model m WHERE o.isActive = 1 AND o.isDeleted = 0 AND o.isExpired = 0"
            , countQuery = "SELECT COUNT(o) FROM Offer o")
    List<Offer> findAll(Sort sort);

    @Query(value = "SELECT DISTINCT o FROM Offer o " +
            "JOIN FETCH o.vehicle v " +
            "JOIN FETCH v.engine e " +
            "JOIN FETCH v.maker mk " +
            "JOIN FETCH v.features vf " +
            "JOIN FETCH v.model m WHERE o.isActive = 1 AND o.isDeleted = 0 AND o.isExpired = 0 AND  o.id = :id")
    @Override
    Optional<Offer> findById(String id);

    Page<Offer> findAllByUserUsername(String username, Pageable pageable);
}
