package org.example;

import lombok.RequiredArgsConstructor;
import org.example.domain.dto.*;
import org.example.domain.entity.Achievement;
import org.example.domain.entity.AchievementTracker;
import org.example.utils.AchievementProgressCalculator;
import org.example.utils.GsonWrapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

@Service
@RequiredArgsConstructor
public class AchievementServiceImp {
    
    private final AchievementRepository achievementRepository;
    private final AchievementProgressCalculator achievementProgressCalculator;
    private final GsonWrapper gsonWrapper;
    
    public List<AchievementTrackerView> getAllAchievementViewsWitherUserId(String userToken) {
        Long userId = getUserId(userToken);
        
        return achievementRepository.findAllByUserId(userId)
            .stream()
            .map(a ->
                new AchievementTrackerView(a,
                    achievementProgressCalculator.getMonthlyProgress(a.getDailyProgress().values().stream().toList()),
                    achievementProgressCalculator.getYearlyProgress(a.getDailyProgress().values().stream().toList()),
                    achievementProgressCalculator.getWeeklyProgress(a.getDailyProgress().values().stream().toList()))
            ).toList();
    }
    
    public AchievementTrackerView getAchievementViewById(String userToken, Long id) {
        Long userId = getUserId(userToken);
        AchievementTracker achTracker = achievementRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new AchievementException("Achievement not found"));
        
        List<AchievementProgressMonthlyView> monthlyProgress = achievementProgressCalculator.getMonthlyProgress(achTracker.getDailyProgress().values().stream().toList());
        List<AchievementProgressYearlyView> yearlyProgress = achievementProgressCalculator.getYearlyProgress(achTracker.getDailyProgress().values().stream().toList());
        List<AchievementProgressWeeklyView> weeklyProgress = achievementProgressCalculator.getWeeklyProgress(achTracker.getDailyProgress().values().stream().toList());
        return new AchievementTrackerView(achTracker, monthlyProgress, yearlyProgress , weeklyProgress);
    }
    
    
    public void createAchievement(String userToken, AchievementHolderCreateDto dto) {
        
        if (achievementRepository.findByName(dto.getName()).isPresent()) {
            throw new AchievementException("Achievement with name " + dto.getName() + " already exists");
        }
        
        AchievementTracker entity = dto.toEntity();
        entity.setStartDate(LocalDate.now());
        entity.setUserId(getUserId(userToken));
        achievementRepository.saveAndFlush(entity);
    }
    
    public void updateAchievement(String userToken, Achievement achievementToAdd, Long achId,
                                  Boolean replaceDailyProgress) {
        
        AchievementTracker achievementTracker = achievementRepository
            .findByIdAndUserId(achId, getUserId(userToken))
            .orElseThrow(() -> new AchievementException("Achievement not found"));
        
        if (achievementToAdd.getDate() == null) {
            throw new AchievementException("Date must be set");
        }
        
        if (achievementToAdd.getProgress() == null || achievementToAdd.getProgress().compareTo(BigDecimal.ZERO) < 0) {
            throw new AchievementException("Progress must be positive");
        }
        
        SortedMap<LocalDate, Achievement> dailyProgress = achievementTracker.getDailyProgress();
        
        if (dailyProgress.isEmpty()) {
            dailyProgress.put(achievementToAdd.getDate(), achievementToAdd);
        } else {
            if (dailyProgress.containsKey(achievementToAdd.getDate())) {
                if (replaceDailyProgress) {
                    dailyProgress.put(achievementToAdd.getDate(), achievementToAdd);
                } else {
                    dailyProgress.get(achievementToAdd.getDate()).setProgress(
                        dailyProgress.get(achievementToAdd.getDate()).getProgress().add(achievementToAdd.getProgress())
                    );
                }
            } else {
                dailyProgress.put(achievementToAdd.getDate(), achievementToAdd);
            }
        }
        
        achievementRepository.saveAndFlush(achievementTracker);
    }
    
    public void deleteAchievement(String userToken, Long id) {
        AchievementTracker achievementTracker = achievementRepository
            .findByIdAndUserId(id, getUserId(userToken))
            .orElseThrow(() -> new AchievementException("Achievement not found"));
        
        achievementRepository.delete(achievementTracker);
    }
    
    public void editTracker(String userToken, AchievementTrackerEditDto dto, Long achId) {
        AchievementTracker achievementTracker = achievementRepository
            .findByIdAndUserId(achId, getUserId(userToken))
            .orElseThrow(() -> new AchievementException("Achievement not found"));
        
        if (dto.getName() != null) {
            achievementRepository
                .findByNameAndUserId(dto.getName(), getUserId(userToken))
                .ifPresent(achievementTracker1 -> {
                    throw new AchievementException("Achievement with name " + dto.getName() + " already exists");
                });
        }
        if (dto.getName() != null && !dto.getName().isEmpty() && !dto.getName().isBlank()) {
            achievementTracker.setName(dto.getName());
        }
        if (dto.getMeasurement() != null && !dto.getMeasurement().isEmpty() && !dto.getMeasurement().isBlank()) {
            achievementTracker.setMeasurement(dto.getMeasurement());
        }
        if (dto.getDescription() != null && !dto.getDescription().isEmpty() && !dto.getDescription().isBlank()) {
            achievementTracker.setDescription(dto.getDescription());
        }
        if (dto.getGoal() != null && dto.getGoal().compareTo(BigDecimal.ZERO) > 0) {
            achievementTracker.setGoal(dto.getGoal());
        }
    }
    
    private Long getUserId(String userToken) {
        return gsonWrapper.fromJson(userToken, User.class).getId();
    }
}
