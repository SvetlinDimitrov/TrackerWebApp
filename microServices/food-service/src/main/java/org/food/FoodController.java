package org.food;

import lombok.RequiredArgsConstructor;
import org.food.domain.dtos.ErrorResponse;
import org.food.domain.dtos.FoodView;
import org.food.exeptions.FoodNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<List<FoodView>> getAllFoods (){
        return new ResponseEntity<>(foodService.getAllFoods() , HttpStatus.OK);
    }

    @GetMapping("/{foodName}")
    public ResponseEntity<FoodView> getFoodByName(@PathVariable String foodName) throws FoodNotFoundException {
        return new ResponseEntity<>(foodService.getFoodByName(foodName) , HttpStatus.OK);
    }

    @ExceptionHandler(FoodNotFoundException.class)
    public ResponseEntity<ErrorResponse> catchMacroNotFoundError(FoodNotFoundException e) {
        return new ResponseEntity<>(e.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }
}
