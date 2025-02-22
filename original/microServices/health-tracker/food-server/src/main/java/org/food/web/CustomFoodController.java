package org.food.web;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.domain.food.paths.CustomFoodControllerPaths;
import org.example.domain.food.shared.FoodCreateRequest;
import org.example.domain.food.shared.FoodView;
import org.food.features.custom.dto.CustomFilterCriteria;
import org.food.features.custom.service.CustomFoodService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomFoodControllerPaths.BASE)
@RequiredArgsConstructor
public class CustomFoodController {

  private final CustomFoodService foodService;

  @GetMapping(CustomFoodControllerPaths.GET_ALL)
  public ResponseEntity<Page<FoodView>> getAll(
      @RequestHeader(name = "X-ViewUser") String userToken,
      Pageable pageable, CustomFilterCriteria filterCriteria) {
    return ResponseEntity.ok(foodService.getAll(userToken, pageable, filterCriteria));
  }

  @GetMapping(CustomFoodControllerPaths.GET_BY_ID)
  public ResponseEntity<FoodView> getById(
      @RequestHeader(name = "X-ViewUser") String userToken, @PathVariable UUID id) {
    return ResponseEntity.ok(foodService.getById(id, userToken));
  }

  @PostMapping(CustomFoodControllerPaths.CREATE)
  public ResponseEntity<FoodView> create(
      @Valid @RequestBody FoodCreateRequest createCustomFood,
      @RequestHeader(name = "X-ViewUser") String userToken) {
    return ResponseEntity.ok(foodService.create(createCustomFood, userToken));
  }

  @DeleteMapping(CustomFoodControllerPaths.DELETE)
  public ResponseEntity<Void> delete(
      @RequestHeader(name = "X-ViewUser") String userToken,
      @PathVariable(name = "id") UUID id) {
    foodService.delete(id, userToken);
    return ResponseEntity.ok().build();
  }
}