package org.record.web;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.domain.food.shared.FoodRequest;
import org.example.domain.food.shared.OwnedFoodView;
import org.example.domain.record.paths.FoodControllerPaths;
import org.record.features.food.dto.FoodFilter;
import org.record.features.food.service.FoodService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FoodControllerPaths.BASE)
@RequiredArgsConstructor
public class FoodController {

  private final FoodService service;

  @GetMapping(FoodControllerPaths.GET_ALL)
  public ResponseEntity<Page<OwnedFoodView>> getAll(
      @PathVariable UUID mealId, Pageable pageable, @Valid FoodFilter filter,
      @RequestHeader("X-ViewUser") String userToken) {
    return new ResponseEntity<>(
        service.getAll(mealId, filter, pageable, userToken),
        HttpStatus.OK);
  }

  @GetMapping(FoodControllerPaths.GET_BY_ID)
  public ResponseEntity<OwnedFoodView> get(
      @PathVariable UUID mealId, @PathVariable UUID foodId,
      @RequestHeader("X-ViewUser") String userToken) {
    return new ResponseEntity<>(
        service.get(foodId, mealId, userToken),
        HttpStatus.OK);
  }

  @PostMapping(FoodControllerPaths.CREATE)
  public ResponseEntity<OwnedFoodView> create(
      @PathVariable UUID mealId, @RequestBody @Valid FoodRequest dto,
      @RequestHeader("X-ViewUser") String userToken) {
    return new ResponseEntity<>(
        service.create(mealId, dto, userToken),
        HttpStatus.CREATED);
  }

  @PutMapping(FoodControllerPaths.UPDATE)
  public ResponseEntity<OwnedFoodView> update(
      @PathVariable UUID mealId, @PathVariable UUID foodId,
      @RequestBody @Valid FoodRequest dto,
      @RequestHeader("X-ViewUser") String userToken) {
    return new ResponseEntity<>(
        service.update(mealId, foodId, dto, userToken),
        HttpStatus.OK);
  }

  @DeleteMapping(FoodControllerPaths.DELETE)
  public ResponseEntity<Void> delete(
      @PathVariable UUID mealId, @PathVariable UUID foodId,
      @RequestHeader("X-ViewUser") String userToken) {
    service.delete(mealId, foodId, userToken);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}