package org.record.features.record.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.record.features.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository
    extends JpaRepository<Record, UUID>, JpaSpecificationExecutor<Record> {

  Optional<Record> findByIdAndUserId(UUID id, UUID userId);

  Record findByDateAndUserId(LocalDate date, UUID userId);

  boolean existsByIdAndUserId(UUID id, UUID userId);

  boolean existsByDateAndUserId(LocalDate date, UUID userId);

  void deleteByIdAndUserId(UUID id, UUID userId);

  void deleteAllByUserId(UUID userId);
}
