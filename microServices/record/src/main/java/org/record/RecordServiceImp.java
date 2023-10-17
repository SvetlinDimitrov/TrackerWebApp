package org.record;

import lombok.RequiredArgsConstructor;
import org.record.client.NutritionIntakeClient;
import org.record.client.NutritionIntakeCreateDto;
import org.record.client.NutritionIntakeView;
import org.record.exeptions.RecordNotFoundException;
import org.record.model.dtos.RecordCreateDto;
import org.record.model.dtos.RecordView;
import org.record.model.entity.Record;
import org.record.model.enums.Gender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordServiceImp {


    private final RecordRepository recordRepository;
    private final NutritionIntakeClient nutritionIntakeClient;

    public List<RecordView> getAllViewsByUserId(Long userId) {
        return recordRepository.findAll()
                .stream()
                .filter(record -> record.getUserId().equals(userId))
                .map(record -> {
                    List<NutritionIntakeView> allNutritionIntakesWithRecordId = nutritionIntakeClient.getAllNutritionIntakesWithRecordId(record.getId());
                    return new RecordView(record.getId(),
                            allNutritionIntakesWithRecordId,
                            record.getDailyCalories(),
                            record.getUserId());
                })
                .collect(Collectors.toList());
    }

    public RecordView getViewByRecordId(Long day) throws RecordNotFoundException {

        return recordRepository.findById(day)
                .map(record -> {
                    List<NutritionIntakeView> allNutritionIntakesWithRecordId = nutritionIntakeClient.getAllNutritionIntakesWithRecordId(record.getId());
                    return new RecordView(record.getId(),
                            allNutritionIntakesWithRecordId,
                            record.getDailyCalories(),
                            record.getUserId());
                })
                .orElseThrow(() -> new RecordNotFoundException(day.toString()));
    }

    public void addNewRecordByUserId(Long userId, RecordCreateDto recordCreateDto) {

        Record record = new Record();

        BigDecimal BMR = getBmr(recordCreateDto);
        BigDecimal caloriesPerDay = getCaloriesPerDay(recordCreateDto, BMR);

        record.setDailyCalories(caloriesPerDay);
        record.setUserId(userId);

        recordRepository.saveAndFlush(record);

        record.setDailyIntakeIds(
                nutritionIntakeClient.createNutritionIntakesWithRecordId(record.getId(),
                                new NutritionIntakeCreateDto(recordCreateDto.gender, caloriesPerDay, recordCreateDto.getWorkoutState()))
                        .stream()
                        .map(NutritionIntakeView::getId)
                        .collect(Collectors.toList())
        );

        recordRepository.saveAndFlush(record);
    }

    public void deleteById(Long recordId, Long userId) throws RecordNotFoundException {
        Record record = recordRepository.findByIdAndUserId(recordId, userId)
                .orElseThrow(() -> new RecordNotFoundException(recordId.toString()));

        nutritionIntakeClient.deleteNutritionIntakesWithRecordId(recordId);

        recordRepository.delete(record);
    }


    private static BigDecimal getCaloriesPerDay(RecordCreateDto recordCreateDto, BigDecimal BMR) {
        return switch (recordCreateDto.workoutState) {
            case SEDENTARY -> BMR.multiply(new BigDecimal("1.2"));
            case LIGHTLY_ACTIVE -> BMR.multiply(new BigDecimal("1.375"));
            case MODERATELY_ACTIVE -> BMR.multiply(new BigDecimal("1.55"));
            case VERY_ACTIVE -> BMR.multiply(new BigDecimal("1.725"));
            case SUPER_ACTIVE -> BMR.multiply(new BigDecimal("1.9"));
        };
    }

    private static BigDecimal getBmr(RecordCreateDto recordCreateDto) {
        BigDecimal BMR;

        if (recordCreateDto.gender.equals(Gender.MALE)) {
            BMR = new BigDecimal("88.362")
                    .add(new BigDecimal("13.397").multiply(recordCreateDto.getKilograms()))
                    .add(new BigDecimal("4.799").multiply(recordCreateDto.getHeight()))
                    .subtract(new BigDecimal("5.677").add(new BigDecimal(recordCreateDto.getAge())));
        } else {
            BMR = new BigDecimal("447.593 ")
                    .add(new BigDecimal("9.247").multiply(recordCreateDto.getKilograms()))
                    .add(new BigDecimal("3.098").multiply(recordCreateDto.getHeight()))
                    .subtract(new BigDecimal("4.330").add(new BigDecimal(recordCreateDto.getAge())));
        }
        return BMR;
    }
}
