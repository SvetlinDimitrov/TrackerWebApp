package org.example.controllers;

import java.util.List;

import org.example.AchievementServiceImp;
import org.example.domain.dto.AchievementHolderCreateDto;
import org.example.domain.dto.AchievementTrackerEditDto;
import org.example.domain.dto.AchievementTrackerView;
import org.example.domain.entity.Achievement;
import org.example.exceptions.AchievementException;
import org.example.exceptions.InvalidJsonTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AchievementControllerImp implements AchievementController {

  private final AchievementServiceImp achievementServiceImp;

  @Override
  public ResponseEntity<List<AchievementTrackerView>> getAllAchievementHoldersById(String userToken)
      throws InvalidJsonTokenException {
    List<AchievementTrackerView> achievementTrackerViews =
        achievementServiceImp.getAllAchievementViewsWitherUserId(userToken);
    return new ResponseEntity<>(achievementTrackerViews, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<AchievementTrackerView> getAchievementHolderById(
      String userToken, String id) throws InvalidJsonTokenException {
    AchievementTrackerView foods = achievementServiceImp.getAchievementViewById(userToken, id);
    return new ResponseEntity<>(foods, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<HttpStatus> createAchievementHolder(
      String userToken, @Valid AchievementHolderCreateDto dto, BindingResult result)
      throws InvalidJsonTokenException {

    if (result.hasErrors()) {
      throw new AchievementException(
          "Invalid data :" + result.getFieldErrors().get(0).getDefaultMessage());
    }

    achievementServiceImp.createAchievement(userToken, dto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<HttpStatus> updateAchievement(
      String userToken, String achId, Boolean replaceDailyProgress, Achievement achievement)
      throws InvalidJsonTokenException {
    achievementServiceImp.updateAchievement(userToken, achievement, achId, replaceDailyProgress);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<HttpStatus> changeAchievementTracker(
      String userToken, String achId, AchievementTrackerEditDto dto)
      throws InvalidJsonTokenException {
    achievementServiceImp.editTracker(userToken, dto, achId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  public ResponseEntity<Void> deleteAchievement(String userToken, String id)
      throws InvalidJsonTokenException {
    achievementServiceImp.deleteAchievement(userToken, id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
