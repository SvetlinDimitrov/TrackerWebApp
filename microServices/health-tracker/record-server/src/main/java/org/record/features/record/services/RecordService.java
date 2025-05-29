package org.record.features.record.services;

import java.util.UUID;
import org.record.features.record.dto.RecordCreateRequest;
import org.record.features.record.dto.RecordUpdateReqeust;
import org.record.features.record.dto.RecordView;
import org.record.features.record.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecordService {

  Page<RecordView> getAll(Pageable pageable);

  RecordView getById(UUID recordId);

  RecordView create(RecordCreateRequest dto);

  RecordView update(UUID id, RecordUpdateReqeust dto);

  void delete(UUID recordId);

  Record findByIdAndUserId(UUID recordId, UUID userId);

  void deleteAllByUserId(UUID userId);

  Record save(Record record);

  boolean isOwner(UUID recordId, UUID userId);
}
