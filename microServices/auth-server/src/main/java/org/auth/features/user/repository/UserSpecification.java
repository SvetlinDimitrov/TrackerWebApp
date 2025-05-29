package org.auth.features.user.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.auth.features.user.dto.UserFilter;
import org.auth.features.user.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public class UserSpecification implements Specification<User> {

  private final UserFilter filter;

  public UserSpecification(UserFilter filter) {
    this.filter = filter;
  }

  @Override
  public Predicate toPredicate(
      @NonNull Root<User> root,
      @NonNull CriteriaQuery<?> query,
      @NonNull CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (filter.username() != null) {
      predicates.add(criteriaBuilder.like(root.get("username"), "%" + filter.username() + "%"));
    }
    if (filter.email() != null) {
      predicates.add(criteriaBuilder.like(root.get("email"), "%" + filter.email() + "%"));
    }
    if (filter.kilograms() != null) {
      predicates.add(criteriaBuilder.equal(root.get("kilograms"), filter.kilograms()));
    }
    if (filter.height() != null) {
      predicates.add(criteriaBuilder.equal(root.get("height"), filter.height()));
    }
    if (filter.age() != null) {
      predicates.add(criteriaBuilder.equal(root.get("age"), filter.age()));
    }
    if (filter.workoutState() != null) {
      predicates.add(criteriaBuilder.equal(root.get("workoutState"), filter.workoutState()));
    }
    if (filter.gender() != null) {
      predicates.add(criteriaBuilder.equal(root.get("gender"), filter.gender()));
    }
    if (filter.role() != null) {
      predicates.add(criteriaBuilder.equal(root.get("role"), filter.role()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
