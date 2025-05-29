const fs = require("fs");
const { chain } = require("stream-chain");
const { parser } = require("stream-json");
const { streamArray } = require("stream-json/streamers/StreamArray");

const sourceFiles = [
  "../../clinetSideData/foodSources/foundationDownload.json",
  "../../clinetSideData/foodSources/FoodData_Central_survey_food_json_2022-10-28.json",
  "../../clinetSideData/foodSources/brandedDownload.json",
];
const outputFiles = [
  "../../runProjectConfig/foodData/foundationFoods.json",
  "../../runProjectConfig/foodData/surveyFoods.json",
  "../../runProjectConfig/foodData/brandedFoods.json",
];



function convertToNewFormat(data) {
  // Create a new object with the desired format
  let newFormat = {
    foodClass: data.foodClass,
    description: data.description,
    calories: {
      name: "Energy",
      amount: 0.0,
      unit: "",
    },
    measurement: "grams",
    size: 100,
    vitaminNutrients: [],
    mineralNutrients: [],
    macroNutrients: []
  };
 
  for (let nutrient of data.foodNutrients) {
    if (nutrient.nutrient.name.includes("Energy")) {
      if (nutrient.nutrient.unitName === "kcal") {
        newFormat.calories.unit = "kcal";
        newFormat.calories.amount = nutrient.amount;
      } else if (nutrient.nutrient.unitName === "kJ") {
        newFormat.calories.unit = "kcal";
        newFormat.calories.amount = nutrient.amount * 0.239006;
      }
    } else if (
      nutrient.nutrient.name === "Vitamin A, RAE" &&
      nutrient.nutrient.unitName === "µg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin A",
          unit: "µg",
          amount: nutrient.amount,
        }
      );
       
    } else if (
      nutrient.nutrient.name === "Vitamin D (D2 + D3)" &&
      nutrient.nutrient.unitName === "µg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin D (D2 + D3)",
          unit: "µg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Vitamin E (alpha-tocopherol)" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin E",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Vitamin K (phylloquinone)" &&
      nutrient.nutrient.unitName === "µg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin K",
          unit: "µg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Vitamin C, total ascorbic acid" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin C",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Thiamin" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin B1 (Thiamin)",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Riboflavin" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin B2 (Riboflavin)",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Niacin" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin B3 (Niacin)",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Pantothenic acid" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin B5 (Pantothenic acid)",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Vitamin B-6" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin B6",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Biotin" &&
      nutrient.nutrient.unitName === "µg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin B7 (Biotin)",
          unit: "µg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Folate, total" &&
      nutrient.nutrient.unitName === "µg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin B9 (Folate)",
          unit: "µg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Vitamin B-12" &&
      nutrient.nutrient.unitName === "µg"
    ) {
      newFormat.vitaminNutrients.push(
        {
          name: "Vitamin B12",
          unit: "µg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Calcium, Ca" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Calcium , Ca",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Phosphorus, P" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Phosphorus , P",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Magnesium, Mg" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Magnesium , Mg",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Sodium, Na" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Sodium , Na",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Potassium, K" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Potassium , K",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Iron, Fe" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Iron , Fe",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Zinc, Zn" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Zinc , Zn",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Copper, Cu" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Copper , Cu",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Manganese, Mn" &&
      nutrient.nutrient.unitName === "mg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Manganese , Mn",
          unit: "mg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Iodine, I" &&
      nutrient.nutrient.unitName === "µg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Iodine , I",
          unit: "µg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Selenium, Se" &&
      nutrient.nutrient.unitName === "µg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Selenium , Se",
          unit: "µg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Molybdenum, Mo" &&
      nutrient.nutrient.unitName === "µg"
    ) {
      newFormat.mineralNutrients.push(
        {
          name: "Molybdenum , Mo",
          unit: "µg",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Carbohydrate, by difference" &&
      nutrient.nutrient.unitName === "g"
    ) {
      newFormat.macroNutrients.push(
        {
          name: "Carbohydrate",
          unit: "g",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Protein" &&
      nutrient.nutrient.unitName === "g"
    ) {
      newFormat.macroNutrients.push(
        {
          name: "Protein",
          unit: "g",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Total lipid (fat)" &&
      nutrient.nutrient.unitName === "g"
    ) {
      newFormat.macroNutrients.push(
        {
          name: "Fat",
          unit: "g",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Fiber, total dietary" &&
      nutrient.nutrient.unitName === "g"
    ) {
      newFormat.macroNutrients.push(
        {
          name: "Fiber",
          unit: "g",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Fatty acids, total trans" &&
      nutrient.nutrient.unitName === "g"
    ) {
      newFormat.macroNutrients.push(
        {
          name: "Trans Fat",
          unit: "g",
          amount: nutrient.amount,
        }
      );
      
    } else if (
      nutrient.nutrient.name === "Fatty acids, total saturated" &&
      nutrient.nutrient.unitName === "g"
    ) {
      newFormat.macroNutrients.push(
        {
          name: "Saturated Fat",
          unit: "g",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Sugars, Total" &&
      nutrient.nutrient.unitName === "g"
    ) {
      newFormat.macroNutrients.push(
        {
          name: "Sugar",
          unit: "g",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Fatty acids, total polyunsaturated" &&
      nutrient.nutrient.unitName === "g"
    ) {
      newFormat.macroNutrients.push(
        {
          name: "Polyunsaturated Fat",
          unit: "g",
          amount: nutrient.amount,
        }
      );
    } else if (
      nutrient.nutrient.name === "Fatty acids, total monounsaturated" &&
      nutrient.nutrient.unitName === "g"
    ) {
      newFormat.macroNutrients.push(
        {
          name: "Monounsaturated Fat",
          unit: "g",
          amount: nutrient.amount,
        }
      );
    }
  }
  if(data.foodClass === "FinalFood"){
    newFormat.foodGroups = data.foodCategory.description;
    newFormat.foodPortions = [];
    for (let portion of data.foodPortions) {
      newFormat.foodPortions.push({
        name: portion.measureUnit.name,
        modifier: portion.modifier,
        gramWeight: portion.gramWeight,
        amount: portion.amount,
      });
    }
  }
  if(data.foodClass === "Survey"){
    newFormat.foodPortions = [];
    for (let portion of data.foodPortions) {
      newFormat.foodPortions.push({
        name: portion.portionDescription,
        gramWeight: portion.gramWeight,
      });
    }
  }
  if (data.foodClass === "Branded") {
    newFormat.foodGroups = data.brandedFoodCategory;
    newFormat.brandedInfo = {
      ingredients: data.ingredients,
      brandOwner: data.brandOwner,
      marketCountry: data.marketCountry,
      servingSize: data.servingSize,
      servingSizeUnit: data.servingSizeUnit,
    };
  }
  if (newFormat.calories.amount !== 0 && newFormat.calories.name === "Energy") {
    return newFormat;
  }
}

sourceFiles.forEach((sourceFile, index) => {
  const outputFile = outputFiles[index];
  const outputFileStream = fs.createWriteStream(outputFile);

  // Write the opening square bracket to the file
  outputFileStream.write("[");

  let isFirstObject = true;

  const pipeline = chain([
    fs.createReadStream(sourceFile),
    parser(),
    streamArray(),
    (data) => {
      const value = data.value;
      const newValue = convertToNewFormat(value);
      return newValue;
    },
    (data) => {
      let jsonText = JSON.stringify(data);
      if (!isFirstObject) {
        jsonText = "," + jsonText; // Add a comma before each JSON object, except the first one
      }
      isFirstObject = false;
      return jsonText + "\n"; // Add a newline character after each JSON object
    },
    outputFileStream,
  ]);

  pipeline.on("error", console.error);

  // When the pipeline has finished, write the closing square bracket to the file
  pipeline.on("finish", () => {
    outputFileStream.end("]");
  });
});
