package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.domain.dto.AchievementHolderCreateDto;
import org.example.domain.dto.AchievementTrackerEditDto;
import org.example.domain.dto.AchievementTrackerView;
import org.example.domain.dto.SingleErrorResponse;
import org.example.domain.entity.Achievement;
import org.example.exceptions.InvalidJsonTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/achievement", headers = "X-ViewUser")
@Tag(
    name = "Achievement",
    description =
        "The Achievement API calculates and provides all the interval dates between the added dates. It provides the weeks, months, and years.")
public interface AchievementController {

  @Operation(
      summary = "Get all achievements by user token",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = AchievementTrackerView.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Json Token",
            content = @Content(schema = @Schema(implementation = SingleErrorResponse.class)))
      })
  @GetMapping(path = "/all")
  ResponseEntity<List<AchievementTrackerView>> getAllAchievementHoldersById(
      @Parameter(
              description = "User token",
              example =
                  "{\"id\":\"1\", \"username\":\"user1\", \"email\":\"user1@example.com\", \"kilograms\":70, \"height\":170, \"age\":30, \"workoutState\":\"ACTIVE\", \"gender\":\"MALE\"}")
          @RequestHeader(name = "X-ViewUser")
          String userToken)
      throws InvalidJsonTokenException;

  @Operation(
      summary = "Get achievement by id",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = AchievementTrackerView.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Json Token",
            content = @Content(schema = @Schema(implementation = SingleErrorResponse.class)))
      })
  @GetMapping
  public ResponseEntity<AchievementTrackerView> getAchievementHolderById(
      @Parameter(
              description = "User token",
              example =
                  "{\"id\":\"1\", \"username\":\"user1\", \"email\":\"user1@example.com\", \"kilograms\":70, \"height\":170, \"age\":30, \"workoutState\":\"ACTIVE\", \"gender\":\"MALE\"}")
          @RequestHeader(name = "X-ViewUser")
          String userToken,
      @Parameter(description = "Achievement id") @RequestParam String id)
      throws InvalidJsonTokenException;

  @Operation(
      summary = "Create a new achievement",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = HttpStatus.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Json Token",
            content = @Content(schema = @Schema(implementation = SingleErrorResponse.class)))
      })
  @PostMapping
  public ResponseEntity<HttpStatus> createAchievementHolder(
      @Parameter(
              description = "User token",
              example =
                  "{\"id\":\"1\", \"username\":\"user1\", \"email\":\"user1@example.com\", \"kilograms\":70, \"height\":170, \"age\":30, \"workoutState\":\"ACTIVE\", \"gender\":\"MALE\"}")
          @RequestHeader(name = "X-ViewUser")
          String userToken,
      @Valid @RequestBody AchievementHolderCreateDto dto,
      BindingResult result)
      throws InvalidJsonTokenException;

  @Operation(
      summary = "Update an achievement",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = HttpStatus.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Json Token",
            content = @Content(schema = @Schema(implementation = SingleErrorResponse.class)))
      })
  @PatchMapping("/{achId}/addProgress")
  public ResponseEntity<HttpStatus> updateAchievement(
      @Parameter(
              description = "User token",
              example =
                  "{\"id\":\"1\", \"username\":\"user1\", \"email\":\"user1@example.com\", \"kilograms\":70, \"height\":170, \"age\":30, \"workoutState\":\"ACTIVE\", \"gender\":\"MALE\"}")
          @RequestHeader(name = "X-ViewUser")
          String userToken,
      @Parameter(description = "Achievement id") @PathVariable String achId,
      @Parameter(description = "Replace daily progress") @RequestParam(defaultValue = "false")
          Boolean replaceDailyProgress,
      @RequestBody Achievement achievement)
      throws InvalidJsonTokenException;

  @Operation(
      summary = "Change achievement tracker",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = HttpStatus.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Json Token",
            content = @Content(schema = @Schema(implementation = SingleErrorResponse.class)))
      })
  @PatchMapping("/{achId}")
  public ResponseEntity<HttpStatus> changeAchievementTracker(
      @Parameter(
              description = "User token",
              example =
                  "{\"id\":\"1\", \"username\":\"user1\", \"email\":\"user1@example.com\", \"kilograms\":70, \"height\":170, \"age\":30, \"workoutState\":\"ACTIVE\", \"gender\":\"MALE\"}")
          @RequestHeader(name = "X-ViewUser")
          String userToken,
      @Parameter(description = "Achievement id") @PathVariable String achId,
      @RequestBody AchievementTrackerEditDto dto)
      throws InvalidJsonTokenException;

  @Operation(
      summary = "Delete an achievement",
      responses = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Json Token",
            content = @Content(schema = @Schema(implementation = SingleErrorResponse.class)))
      })
  @DeleteMapping
  public ResponseEntity<Void> deleteAchievement(
      @Parameter(
              description = "User token",
              example =
                  "{\"id\":\"1\", \"username\":\"user1\", \"email\":\"user1@example.com\", \"kilograms\":70, \"height\":170, \"age\":30, \"workoutState\":\"ACTIVE\", \"gender\":\"MALE\"}")
          @RequestHeader(name = "X-ViewUser")
          String userToken,
      @Parameter(description = "Achievement id") @RequestParam String id)
      throws InvalidJsonTokenException;
}
