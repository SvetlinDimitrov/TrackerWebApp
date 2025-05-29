package org.food.features.custom.repository.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.domain.user.dto.UserView;
import org.example.domain.user.enums.UserRole;
import org.food.features.custom.dto.CustomFilterCriteria;
import org.food.features.custom.entity.CustomFood;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
public class CustomFoodSpecification implements Specification<CustomFood> {

  private final UserView user;
  private final CustomFilterCriteria criteria;

  @Override
  public Predicate toPredicate(
      @NonNull Root<CustomFood> root,
      @NonNull CriteriaQuery<?> query,
      @NonNull CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    if (UserRole.ADMIN != user.role()) {
      predicates.add(cb.equal(root.get("userId"), user.id()));
    }

    if (criteria.name() != null && !criteria.name().isEmpty()) {
      predicates.add(
          cb.like(cb.lower(root.get("description")), "%" + criteria.name().toLowerCase() + "%"));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
