package org.record.features.record.services;

import static org.record.infrastructure.exception.ExceptionMessages.RECORD_NOT_FOUND;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.exceptions.throwable.NotFoundException;
import org.record.features.record.dto.RecordCreateRequest;
import org.record.features.record.dto.RecordUpdateReqeust;
import org.record.features.record.dto.RecordView;
import org.record.features.record.entity.Record;
import org.record.features.record.repository.RecordRepository;
import org.record.features.record.repository.RecordSpecification;
import org.record.infrastructure.config.security.SecurityUtils;
import org.record.infrastructure.mappers.RecordMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecordServiceImp implements RecordService {

  private final RecordMapper recordMapper;
  private final RecordRepository repository;

  public Page<RecordView> getAll(Pageable pageable) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    return repository.findAll(new RecordSpecification(user), pageable).map(recordMapper::toView);
  }

  public RecordView getById(UUID recordId) {
    return recordMapper.toView(findById(recordId));
  }

  public RecordView create(RecordCreateRequest dto) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    if (repository.existsByDateAndUserId(dto.date(), user.id())) {
      return recordMapper.toView(repository.findByDateAndUserId(dto.date(), user.id()));
    }

    var record = recordMapper.toEntity(dto, user);

    return recordMapper.toView(repository.save(record));
  }

  public RecordView update(UUID id, RecordUpdateReqeust dto) {
    var record = findById(id);

    recordMapper.update(record, dto);

    return recordMapper.toView(repository.save(record));
  }

  @Transactional
  public void delete(UUID recordId) {
    if (!repository.existsById(recordId)) {
      throw new NotFoundException(RECORD_NOT_FOUND, recordId);
    }

    repository.deleteById(recordId);
  }

  public Record findByIdAndUserId(UUID recordId, UUID userId) {
    return repository
        .findByIdAndUserId(recordId, userId)
        .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND, recordId));
  }

  public Record findById(UUID recordId) {
    return repository
        .findById(recordId)
        .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND, recordId));
  }

  @Transactional
  public void deleteAllByUserId(UUID userId) {
    repository.deleteAllByUserId(userId);
  }

  public Record save(Record record) {
    return repository.save(record);
  }

  public boolean isOwner(UUID recordId, UUID userId) {
    return repository.existsByIdAndUserId(recordId, userId);
  }
}
