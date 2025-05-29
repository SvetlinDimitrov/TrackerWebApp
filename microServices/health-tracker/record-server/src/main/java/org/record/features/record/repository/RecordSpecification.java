package org.record.features.record.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.user.dto.UserView;
import org.record.features.record.entity.Record;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
public class RecordSpecification implements Specification<Record> {

  private final UserView user;

  @Override
  public Predicate toPredicate(
      @NonNull Root<Record> root,
      @NonNull CriteriaQuery<?> query,
      @NonNull CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (user.id() != null) {
      predicates.add(criteriaBuilder.equal(root.get("userId"), user.id()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
