
export const featureNutritionDate = (
  allFoods,
  user,
  type,
  sort,
  dataLength
) => {
  let data = {
    immuneFunction: immuneSystem(allFoods, user),
    growthAndDevelopment: growthAndDevelopmentText(allFoods, user),
    cognitiveFunctionAndBrainHealth: brainHealthText(allFoods, user),
    boneHealth: boneHealthText(allFoods, user),
    physicalPerformanceAndFitness: fitnessText(allFoods, user),
    agingAndLongevity: agingText(allFoods, user),
  };
  return data[type];
};

const getPercentageForVitaminA = (nutrient, gender) => {
  if (gender === "MALE") {
    return (nutrient / 900) * 100;
  }
  return (nutrient / 700) * 100;
};

const getPercentageForVitaminD = (nutrient) => {
  return (nutrient / 800) * 100;
};

const getPercentageForVitaminE = (nutrient) => {
  return (nutrient / 15) * 100;
};

const getPercentageForVitaminK = (nutrient, gender) => {
  if (gender === "MALE") {
    return (nutrient / 120) * 100;
  }
  return (nutrient / 90) * 100;
};

const getPercentageForVitaminC = (nutrient, gender) => {
  if (gender === "MALE") {
    return (nutrient / 90) * 100;
  }
  return (nutrient / 75) * 100;
};

const getPercentageForVitaminB6 = (nutrient, gender) => {
  if (gender === "MALE") {
    return (nutrient / 1.7) * 100;
  }
  return (nutrient / 1.5) * 100;
};

const getPercentageForVitaminB9 = (nutrient) => {
  return (nutrient / 400) * 100;
};
const getPercentageForVitaminB12 = (nutrient) => {
  return (nutrient / 2.4) * 100;
};

const getPercentageForCalcium = (nutrient) => {
  return (nutrient / 1300) * 100;
};
const getPercentageForPhosphorus = (nutrient) => {
  return (nutrient / 1250) * 100;
};
const getPercentageForMagnesium = (nutrient, gender) => {
  if (gender === "MALE") {
    return (nutrient / 420) * 100;
  }
  return (nutrient / 320) * 100;
};

const getPercentageForPotassium = (nutrient) => {
  return (nutrient / 3400) * 100;
};

const getPercentageForIron = (nutrient) => {
  return (nutrient / 11) * 100;
};
const getPercentageForZinc = (nutrient, gender) => {
  if (gender === "MALE") {
    return (nutrient / 11) * 100;
  }
  return (nutrient / 8) * 100;
};

const getPercentageForSelenium = (nutrient) => {
  return (nutrient / 400) * 100;
};

const getPercentageForFiber = (nutrient) => {
  return (nutrient / 38) * 100;
};

const immuneSystem = (allFoods, user) => {
  const notSortedData = allFoods.map((food) =>
    getAllPercentageForImmuneSystem(
      food.c,
      food.d,
      food.a,
      food.e,
      food.b6,
      food.b9,
      food.b12,
      food.zinc,
      food.selenium,
      food.iron,
      food.protein,
      food.fat,
      user.gender,
      food.name
    )
  );
  let processedData = notSortedData.map((item) => {
    const average = (
      (item.C +
        item.D +
        item.A +
        item.E +
        item.B6 +
        item.B9 +
        item.B12 +
        item.Zinc +
        item.Selenium +
        item.Iron) /
      10
    ).toFixed(2);
    const typeData = `Vitamin C: ${item.C.toFixed(
      2
    )}%\nVitamin D: ${item.D.toFixed(2)}%\nVitamin A: ${item.A.toFixed(
      2
    )}%\nVitamin E: ${item.E.toFixed(2)}%\nVitamin B6: ${item.B6.toFixed(
      2
    )}%\nVitamin B9: ${item.B9.toFixed(2)}%\nVitamin B12: ${item.B12.toFixed(
      2
    )}%\nZinc: ${item.Zinc.toFixed(2)}%\nSelenium : ${item.Selenium.toFixed(
      2
    )}%\nIron: ${item.Iron.toFixed(2)}%\nProtein: ${item.Protein.toFixed(
      2
    )}g\nFat: ${item.Fat.toFixed(2)}g\nTotal %: ${average}`;

    return { data: average, dataNames: item.name, typeData, item };
  });

  return processedData.sort((a, b) => {
    if (b.data === a.data) {
      if (b.item.Protein === a.item.Protein) {
        return b.item.Fat - a.itemFat;
      }
      return b.item.Protein - a.item.Protein;
    }
    return b.data - a.data;
  });
};
const growthAndDevelopmentText = (allFoods, user) => {
  const notSortedData = allFoods.map((food) =>
    getAllPercentageForGrowthAndDevelopmentText(
      food.c,
      food.d,
      food.a,
      food.e,
      food.b9,
      food.b12,
      food.zinc,
      food.calcium,
      food.iron,
      food.protein,
      food.fat,
      user.gender,
      food.name
    )
  );
  let processedData = notSortedData.map((i) => {
    const average = (
      (i.C + i.D + i.A + i.E + i.B9 + i.B12 + i.Zinc + i.Calcium + i.Iron) /
      9
    ).toFixed(2);
    const dataNames = i.name;
    const typeData = `Vitamin C: ${i.C.toFixed(2)}%\nVitamin D: ${i.D.toFixed(
      2
    )}%\nVitamin A: ${i.A.toFixed(2)}%\nVitamin E: ${i.E.toFixed(
      2
    )}%\nVitamin B9: ${i.B9.toFixed(2)}%\nVitamin B12: ${i.B12.toFixed(
      2
    )}%\nZinc: ${i.Zinc.toFixed(2)}%\nCalcium : ${i.Calcium.toFixed(
      2
    )}%\nIron: ${i.Iron.toFixed(2)}%\nProtein: ${i.Protein.toFixed(
      2
    )}g\nFat: ${i.Fat.toFixed(2)}g\nTotal %: ${average}`;
    return { ...i, data: average, dataNames, typeData, i };
  });

  return processedData.sort((a, b) => {
    if (b.data === a.data) {
      if (b.Protein === a.Protein) {
        return b.Fat - a.Fat;
      }
      return b.Protein - a.Protein;
    }
    return b.data - a.data;
  });
};
const brainHealthText = (allFoods, user) => {
  const notSortedData = allFoods.map((food) =>
    getAllPercentageForCognitiveFunctionAndBrainHealth(
      food.e,
      food.c,
      food.b6,
      food.b12,
      food.iron,
      food.fat,
      user.gender,
      food.name
    )
  );
  let processedData = notSortedData.map((item) => {
    const average = (
      (item.E + item.C + item.B6 + item.B12 + item.Iron) /
      5
    ).toFixed(2);
    const typeData = `Vitamin C: ${item.C.toFixed(
      2
    )}%\nVitamin E: ${item.E.toFixed(2)}%\nVitamin B6: ${item.B6.toFixed(
      2
    )}%\nVitamin B12: ${item.B12.toFixed(2)}%\nIron: ${item.Iron.toFixed(
      2
    )}%\nFat: ${item.Fat.toFixed(2)}g\nTotal %: ${average}`;

    return { data: average, dataNames: item.name, typeData, item };
  });

  return processedData.sort((a, b) => {
    if (b.data === a.data) {
      return b.item.Fat - a.item.Fat;
    }
    return b.data - a.data;
  });
};
const boneHealthText = (allFoods, user) => {
  const notSortedData = allFoods.map((food) =>
    getAllPercentageForBoneHealth(
      food.d,
      food.k,
      food.calcium,
      food.phosphorus,
      food.magnesium,
      food.protein,
      user.gender,
      food.name
    )
  );
  let processedData = notSortedData.map((item) => {
    const average = (
      (item.D + item.K + item.Phosphorus + item.Magnesium + item.Calcium) /
      5
    ).toFixed(2);
    const typeData = `Vitamin D: ${item.D.toFixed(
      2
    )}%\nVitamin K: ${item.K.toFixed(2)}%\nCalcium: ${item.Calcium.toFixed(
      2
    )}%\nPhosphorus: ${item.Phosphorus.toFixed(
      2
    )}%\nMagnesium: ${item.Magnesium.toFixed(
      2
    )}%\nProtein: ${item.Protein.toFixed(2)}g\nTotal %: ${average}`;

    return { data: average, dataNames: item.name, typeData, item };
  });

  return processedData.sort((a, b) => {
    if (b.data === a.data) {
      return b.item.Protein - a.item.Protein;
    }
    return b.data - a.data;
  });
};
const fitnessText = (allFoods, user) => {
  const notSortedData = allFoods.map((food) =>
    getAllPercentageForPhysicalPerformance(
      food.b6,
      food.b12,
      food.iron,
      food.potassium,
      food.protein,
      food.fat,
      food.carbohydrates,
      user.gender,
      food.name
    )
  );
  let processedData = notSortedData.map((item) => {
    const average = (
      (item.B6 + item.B12 + item.Potassium + item.Iron) /
      4
    ).toFixed(2);
    const typeData = `Vitamin B6: ${item.B6.toFixed(
      2
    )}%\nVitamin B12: ${item.B12.toFixed(2)}%\nIron: ${item.Iron.toFixed(
      2
    )}%\nPotassium: ${item.Potassium.toFixed(
      2
    )}%\nProtein: ${item.Protein.toFixed(
      2
    )}g\nCarbohydrates: ${item.Carbohydrates.toFixed(
      2
    )}g\nFat: ${item.Fat.toFixed(2)}g\nTotal %: ${average}`;

    return { data: average, dataNames: item.name, typeData, item };
  });

  return processedData.sort((a, b) => {
    if (b.data === a.data) {
      if (b.item.Protein === a.item.Protein) {
        if (b.item.Fat === a.item.Fat) {
          return b.item.Carbohydrates - a.item.Carbohydrates;
        }
        return b.item.Fat - a.item.Fat;
      }
      return b.item.Protein - a.item.Protein;
    }
    return b.data - a.data;
  });
};
const agingText = (allFoods, user) => {
  const notSortedData = allFoods.map((food) =>
    getAllPercentageForAging(
      food.c,
      food.e,
      food.d,
      food.fiber,
      food.protein,
      user.gender,
      food.name
    )
  );
  let processedData = notSortedData.map((item) => {
    const average = ((item.C + item.D + item.E + item.Fiber) / 4).toFixed(2);
    const typeData = `Vitamin C: ${item.C.toFixed(
      2
    )}%\nVitamin E: ${item.E.toFixed(2)}%\nVitamin D: ${item.D.toFixed(
      2
    )}%\nFiber: ${item.Fiber.toFixed(2)}%\nProtein: ${item.Protein.toFixed(
      2
    )}g\nTotal %: ${average}`;

    return { data: average, dataNames: item.name, typeData, item };
  });

  return processedData.sort((a, b) => {
    if (b.data === a.data) {
      return b.item.Protein - a.item.Protein;
    }
    return b.data - a.data;
  });
};
const getAllPercentageForImmuneSystem = (
  C,
  D,
  A,
  E,
  B6,
  B9,
  B12,
  Zinc,
  Selenium,
  Iron,
  Protein,
  Fat,
  Gender,
  name
) => {
  return {
    C:
      getPercentageForVitaminC(C, Gender) > 100
        ? 100
        : getPercentageForVitaminC(C, Gender),
    D:
      getPercentageForVitaminD(D, Gender) > 100
        ? 100
        : getPercentageForVitaminD(D, Gender),
    A:
      getPercentageForVitaminA(A, Gender) > 100
        ? 100
        : getPercentageForVitaminA(A, Gender),
    E:
      getPercentageForVitaminE(E, Gender) > 100
        ? 100
        : getPercentageForVitaminE(E, Gender),
    B6:
      getPercentageForVitaminB6(B6, Gender) > 100
        ? 100
        : getPercentageForVitaminB6(B6, Gender),
    B9:
      getPercentageForVitaminB9(B9, Gender) > 100
        ? 100
        : getPercentageForVitaminB9(B9, Gender),
    B12:
      getPercentageForVitaminB12(B12, Gender) > 100
        ? 100
        : getPercentageForVitaminB12(B12, Gender),
    Zinc:
      getPercentageForZinc(Zinc, Gender) > 100
        ? 100
        : getPercentageForZinc(Zinc, Gender),
    Selenium:
      getPercentageForSelenium(Selenium, Gender) > 100
        ? 100
        : getPercentageForSelenium(Selenium, Gender),
    Iron: getPercentageForIron(Iron) > 100 ? 100 : getPercentageForIron(Iron),
    Protein,
    Fat,
    name,
  };
};
const getAllPercentageForGrowthAndDevelopmentText = (
  C,
  D,
  A,
  E,
  B9,
  B12,
  Zinc,
  Calcium,
  Iron,
  Protein,
  Fat,
  Gender,
  name
) => {
  return {
    C:
      getPercentageForVitaminC(C, Gender) > 100
        ? 100
        : getPercentageForVitaminC(C, Gender),
    D:
      getPercentageForVitaminD(D, Gender) > 100
        ? 100
        : getPercentageForVitaminD(D, Gender),
    A:
      getPercentageForVitaminA(A, Gender) > 100
        ? 100
        : getPercentageForVitaminA(A, Gender),
    E:
      getPercentageForVitaminE(E, Gender) > 100
        ? 100
        : getPercentageForVitaminE(E, Gender),
    B9:
      getPercentageForVitaminB9(B9, Gender) > 100
        ? 100
        : getPercentageForVitaminB9(B9, Gender),
    B12:
      getPercentageForVitaminB12(B12, Gender) > 100
        ? 100
        : getPercentageForVitaminB12(B12, Gender),
    Zinc:
      getPercentageForZinc(Zinc, Gender) > 100
        ? 100
        : getPercentageForZinc(Zinc, Gender),
    Calcium:
      getPercentageForCalcium(Calcium, Gender) > 100
        ? 100
        : getPercentageForCalcium(Calcium, Gender),
    Iron: getPercentageForIron(Iron) > 100 ? 100 : getPercentageForIron(Iron),
    Protein,
    Fat,
    name,
  };
};
const getAllPercentageForCognitiveFunctionAndBrainHealth = (
  E,
  C,
  B6,
  B12,
  Iron,
  Fat,
  Gender,
  name
) => {
  return {
    E:
      getPercentageForVitaminE(E, Gender) > 100
        ? 100
        : getPercentageForVitaminE(E, Gender),
    C:
      getPercentageForVitaminC(C, Gender) > 100
        ? 100
        : getPercentageForVitaminC(C, Gender),
    B6:
      getPercentageForVitaminB6(B6, Gender) > 100
        ? 100
        : getPercentageForVitaminB6(B6, Gender),
    B12:
      getPercentageForVitaminB12(B12, Gender) > 100
        ? 100
        : getPercentageForVitaminB12(B12, Gender),
    Iron:
      getPercentageForIron(Iron, Gender) > 100
        ? 100
        : getPercentageForIron(Iron, Gender),
    Fat,
    name,
  };
};
const getAllPercentageForBoneHealth = (
  D,
  K,
  Calcium,
  Phosphorus,
  Magnesium,
  Protein,
  Gender,
  name
) => {
  return {
    D:
      getPercentageForVitaminD(D, Gender) > 100
        ? 100
        : getPercentageForVitaminD(D, Gender),
    K:
      getPercentageForVitaminK(K, Gender) > 100
        ? 100
        : getPercentageForVitaminK(K, Gender),
    Calcium:
      getPercentageForCalcium(Calcium, Gender) > 100
        ? 100
        : getPercentageForCalcium(Calcium, Gender),
    Phosphorus:
      getPercentageForPhosphorus(Phosphorus, Gender) > 100
        ? 100
        : getPercentageForPhosphorus(Phosphorus, Gender),
    Magnesium:
      getPercentageForMagnesium(Magnesium, Gender) > 100
        ? 100
        : getPercentageForMagnesium(Magnesium, Gender),

    Protein,
    name,
  };
};
const getAllPercentageForPhysicalPerformance = (
  B6,
  B12,
  Iron,
  Potassium,
  Protein,
  Fat,
  Carbohydrates,
  Gender,
  name
) => {
  return {
    B6:
      getPercentageForVitaminB6(B6, Gender) > 100
        ? 100
        : getPercentageForVitaminB6(B6, Gender),
    B12:
      getPercentageForVitaminB12(B12, Gender) > 100
        ? 100
        : getPercentageForVitaminB12(B12, Gender),
    Iron:
      getPercentageForIron(Iron, Gender) > 100
        ? 100
        : getPercentageForIron(Iron, Gender),
    Potassium:
      getPercentageForPotassium(Potassium, Gender) > 100
        ? 100
        : getPercentageForPotassium(Potassium, Gender),
    Protein,
    Fat,
    Carbohydrates,
    name,
  };
};
const getAllPercentageForAging = (C, E, D, Fiber, Protein, Gender, name) => {
  return {
    C:
      getPercentageForVitaminC(C, Gender) > 100
        ? 100
        : getPercentageForVitaminC(C, Gender),
    E:
      getPercentageForVitaminB12(E, Gender) > 100
        ? 100
        : getPercentageForVitaminB12(E, Gender),
    D:
      getPercentageForVitaminD(D, Gender) > 100
        ? 100
        : getPercentageForVitaminD(D, Gender),
    Fiber:
      getPercentageForFiber(Fiber, Gender) > 100
        ? 100
        : getPercentageForFiber(Fiber, Gender),
    Protein,
    name,
  };
};
