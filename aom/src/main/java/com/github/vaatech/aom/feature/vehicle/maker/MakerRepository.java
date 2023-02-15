package com.github.vaatech.aom.feature.vehicle.maker;

import com.github.vaatech.aom.feature.common.repository.ApplicationJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MakerRepository extends ApplicationJpaRepository<Maker, Long>
{
  @Query(
      "SELECT DISTINCT "
          + "m FROM Maker m "
          + "LEFT JOIN FETCH m.models mo "
          + "WHERE m.name = :name")
  Optional<Maker> findByName(String name);

  @Query("SELECT DISTINCT m FROM Maker m LEFT JOIN FETCH m.models mo WHERE m.id = :id")
  Optional<Maker> findMakerById(Long id);

  @Query("SELECT DISTINCT m FROM Maker m LEFT JOIN FETCH m.models mo")
  List<Maker> findAllWithModels();

  boolean existsByName(String name);


}
