package org.record.features.record.services;

import jakarta.validation.Valid;
import java.util.UUID;
import org.record.features.record.dto.RecordUpdateReqeust;
import org.record.features.record.dto.RecordView;
import org.record.features.record.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecordService {

  Page<RecordView> getAll(String userToken, Pageable pageable);

  RecordView getById(UUID recordId, String userToken);

  RecordView create(String userToken);

  RecordView update(UUID id, String userToken, @Valid RecordUpdateReqeust dto);

  void delete(UUID recordId, String userToken);

  Record findByIdAndUserId(UUID recordId, UUID userId);
}
