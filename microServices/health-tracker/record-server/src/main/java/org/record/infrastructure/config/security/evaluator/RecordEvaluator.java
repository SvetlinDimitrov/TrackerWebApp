package org.record.infrastructure.config.security.evaluator;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.domain.user.dto.UserView;

import org.record.features.record.services.RecordService;
import org.record.infrastructure.config.security.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecordEvaluator {

  private final RecordService service;

  public boolean isOwner(UUID recordId) {
    UserView user = SecurityUtils.getCurrentLoggedInUser();

    return service.isOwner(recordId, user.id());
  }
}
