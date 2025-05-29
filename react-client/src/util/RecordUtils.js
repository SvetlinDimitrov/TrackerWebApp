export const calculatedPrecentedValues = (rawData, type) => {
  if (type === "Macronutrient") {
    return rawData.nutritionIntakesViews
      .filter(
        (item) =>
          item.nutrientType === type ||
          item.nutrientType === "Carbohydrate" ||
          item.nutrientType === "Fat"
      )
      .map((item) => ({
        name: item.nutrientName,
        precented: (item.dailyConsumed / item.upperBoundIntake) * 100,
        consumed: item.dailyConsumed,
        max: item.upperBoundIntake,
        type: item.nutrientType,
        measurement: item.measurement,
      }));
  }
  if (type === "Calories") {
    return rawData.storageViews.map((item) => ({
      name: item.name,
      precented: (item.consumedCalories / rawData.dailyConsumedCalories) * 100,
      consumed: item.consumedCalories,
      max: rawData.dailyConsumedCalories,
      type: "Calories",
      measurement: "kcal",
      fat: item.foods.reduce((total, food) => {
        const fatNutrient = food.macroNutrients.find(
          (nutrient) => nutrient.name === "Fat"
        );
        return total + (fatNutrient ? fatNutrient.amount : 0);
      }, 0),
      carbohydrates: item.foods.reduce((total, food) => {
        const fatNutrient = food.macroNutrients.find(
          (nutrient) => nutrient.name === "Carbohydrate"
        );
        return total + (fatNutrient ? fatNutrient.amount : 0);
      }, 0),
      protein: item.foods.reduce((total, food) => {
        const fatNutrient = food.macroNutrients.find(
          (nutrient) => nutrient.name === "Protein"
        );
        return total + (fatNutrient ? fatNutrient.amount : 0);
      }, 0),
    }));
  }
  return rawData.nutritionIntakesViews
    .filter((item) => item.nutrientType === type)
    .map((item) => ({
      name: item.nutrientName,
      precented: (item.dailyConsumed / item.upperBoundIntake) * 100,
      consumed: item.dailyConsumed,
      max: item.upperBoundIntake,
      type: item.nutrientType,
      measurement: item.measurement,
    }));
};

export const calcAverageValue = (rawData, type) => {
  const precentedValues = rawData.nutritionIntakesViews
    .filter((item) => item.nutrientType === type)
    .map((item) =>
      (item.dailyConsumed / item.upperBoundIntake) * 100 > 100
        ? 100
        : (item.dailyConsumed / item.upperBoundIntake) * 100
    );

  return (
    precentedValues.reduce((sum, value) => sum + value, 0) /
    precentedValues.length
  );
};

export const getRecordById = (allRecords, id) => {
  return allRecords.find((record) => record.id === id);
};

export const getStorageById = (record, storageId) => {
  return record.storageViews.find((storage) => {
    return storage.id === +storageId;
  });
};

export const getTotalFoodCaloriesFromStorage = (storage) => {
  return storage.foods.reduce((total, food) => total + food.calories, 0);
};
