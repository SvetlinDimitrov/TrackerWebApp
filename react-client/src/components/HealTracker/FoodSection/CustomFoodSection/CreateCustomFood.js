import React, { useState, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styles from "./CreateCustomFood.module.css";

import { AuthContext } from "../../../../context/UserCredentials";
import { NotificationContext } from "../../../../context/Notification";
import api from "../../../../util/api";
import * as PathCreator from "../../../../util/PathCreator";

const nutrientsCreationInfoBluePrint = {
  "Vitamin A": {
    name: "Vitamin A",
    unit: "µg",
    amount: 0,
  },
  "Vitamin D (D2 + D3)": {
    name: "Vitamin D (D2 + D3)",
    unit: "µg",
    amount: 0,
  },
  "Vitamin E": {
    name: "Vitamin E",
    unit: "mg",
    amount: 0,
  },
  "Vitamin K": {
    name: "Vitamin K",
    unit: "µg",
    amount: 0,
  },
  "Vitamin C": {
    name: "Vitamin C",
    unit: "mg",
    amount: 0,
  },
  "Vitamin B1 (Thiamin)": {
    name: "Vitamin B1 (Thiamin)",
    unit: "mg",
    amount: 0,
  },
  "Vitamin B2 (Riboflavin)": {
    name: "Vitamin B2 (Riboflavin)",
    unit: "mg",
    amount: 0,
  },
  "Vitamin B3 (Niacin)": {
    name: "Vitamin B3 (Niacin)",
    unit: "mg",
    amount: 0,
  },
  "Vitamin B5 (Pantothenic acid)": {
    name: "Vitamin B5 (Pantothenic acid)",
    unit: "mg",
    amount: 0,
  },
  "Vitamin B6": {
    name: "Vitamin B6",
    unit: "mg",
    amount: 0,
  },
  "Vitamin B7 (Biotin)": {
    name: "Vitamin B7 (Biotin)",
    unit: "µg",
    amount: 0,
  },
  "Vitamin B9 (Folate)": {
    name: "Vitamin B9 (Folate)",
    unit: "µg",
    amount: 0,
  },
  "Vitamin B12": {
    name: "Vitamin B12",
    unit: "µg",
    amount: 0,
  },
  "Calcium , Ca": {
    name: "Calcium , Ca",
    unit: "mg",
    amount: 0,
  },
  "Phosphorus , P": {
    name: "Phosphorus , P",
    unit: "mg",
    amount: 0,
  },
  "Magnesium , Mg": {
    name: "Magnesium , Mg",
    unit: "mg",
    amount: 0,
  },
  "Sodium , Na": {
    name: "Sodium , Na",
    unit: "mg",
    amount: 0,
  },
  "Potassium , K": {
    name: "Potassium , K",
    unit: "mg",
    amount: 0,
  },
  "Iron , Fe": {
    name: "Iron , Fe",
    unit: "mg",
    amount: 0,
  },
  "Zinc , Zn": {
    name: "Zinc , Zn",
    unit: "mg",
    amount: 0,
  },
  "Copper , Cu": {
    name: "Copper , Cu",
    unit: "mg",
    amount: 0,
  },
  "Manganese , Mn": {
    name: "Manganese , Mn",
    unit: "mg",
    amount: 0,
  },
  "Selenium , Se": {
    name: "Selenium , Se",
    unit: "µg",
    amount: 0,
  },
  "Iodine , I": {
    name: "Iodine , I",
    unit: "µg",
    amount: 0,
  },
  "Molybdenum , Mo": {
    name: "Molybdenum , Mo",
    unit: "µg",
    amount: 0,
  },
  Carbohydrate: {
    name: "Carbohydrate",
    unit: "g",
    amount: 0,
  },
  Protein: {
    name: "Protein",
    unit: "g",
    amount: 0,
  },
  Fat: {
    name: "Fat",
    unit: "g",
    amount: 0,
  },
  Fiber: {
    name: "Fiber",
    unit: "g",
    amount: 0,
  },
  Sugar: {
    name: "Sugar",
    unit: "g",
    amount: 0,
  },
  "Saturated Fat": {
    name: "Saturated Fat",
    unit: "g",
    amount: 0,
  },
  "Monounsaturated Fat": {
    name: "Monounsaturated Fat",
    unit: "g",
    amount: 0,
  },
  "Trans Fat": {
    name: "Trans Fat",
    unit: "g",
    amount: 0,
  },
  "Polyunsaturated Fat": {
    name: "Polyunsaturated Fat",
    unit: "g",
    amount: 0,
  },
};
const CreateCustomFood = () => {
  const [foodItem, setFoodItem] = useState({
    description: "",
    size: "100.00",
    calories: "0.00",
    vitaminNutrients: [],
    macroNutrients: [],
    mineralNutrients: [],
  });
  const [nutrientsCreationInfo, setNutrientsCreationInfo] = useState(
    nutrientsCreationInfoBluePrint
  );
  const { user } = useContext(AuthContext);
  const { setSuccessfulMessage, setFailedMessage } =
    useContext(NotificationContext);
  const { recordId, storageId } = useParams();
  const [addNutrientsState, setAddNutrientsState] = useState();
  const [addNutrient, setAddNutrient] = useState("");
  const navigate = useNavigate();
  const userToken = user.tokenInfo.token;

  const handleChange = (e) => {
    setFoodItem({
      ...foodItem,
      [e.target.name]: e.target.value,
    });
  };
  const handleSubmit = async (event) => {
    event.preventDefault();

    Object.keys(nutrientsCreationInfo).forEach((key) => {
      if (nutrientsCreationInfo[key].amount > 0) {
        if (key.includes("Vitamin")) {
          foodItem.vitaminNutrients.push(nutrientsCreationInfo[key]);
        } else if (key.includes(",")) {
          foodItem.mineralNutrients.push(nutrientsCreationInfo[key]);
        } else {
          foodItem.macroNutrients.push(nutrientsCreationInfo[key]);
        }
      }
    });

    try {
      await api.post("/food", foodItem, {
        headers: { Authorization: `Bearer ${userToken}` },
      });
      setSuccessfulMessage({
        message: "FOOD: " + foodItem.name + " was created successfully!",
        flag: true,
      });
    } catch (error) {
      if (
        error.response.status === 400 &&
        error.response.data.message ===
          "Food with name " + foodItem.name + " already exists."
      ) {
        setFailedMessage({
          message: "Food with name " + foodItem.name + " already exists.",
          flag: true,
        });
      } else {
        setFailedMessage({
          message:
            "Something went wrong with record creation. Please try again later!",
          flag: true,
        });
      }
    }
    navigate(PathCreator.customFoodPath(recordId, storageId));
  };
  const onClose = () => {
    navigate(PathCreator.customFoodPath(recordId, storageId));
  };

  return (
    <div className={styles.overlay}>
      <button className={styles.closeButton} onClick={onClose}>
        X
      </button>
      <div className={styles.popupForm}>
        <h1 className={styles.title}>Create Custom Food</h1>
        <div className={styles.buttonGroup}>
          <button
            className={styles.groupButton}
            onClick={() => {
              setAddNutrient("");
              setAddNutrientsState(!addNutrientsState);
            }}
          >
            Show Nutrients
          </button>
        </div>
        <form className={styles.form} onSubmit={handleSubmit}>
          <div className={styles.inputGroup}>
            <label className={styles.label} htmlFor="description">
              Description
            </label>
            <input
              id="description"
              name="description"
              required
              type="text"
              minLength={3}
              className={styles.input}
              value={foodItem.description}
              onChange={handleChange}
            />
          </div>
          <div className={styles.inputGroup}>
            <label className={styles.label} htmlFor="size">
              Size
            </label>
            <input
              id="size"
              name="size"
              required
              type="number"
              min={1}
              className={styles.input}
              value={foodItem.size}
              onChange={handleChange}
            />
          </div>
          <div className={styles.inputGroup}>
            <label className={styles.label} htmlFor="calories">
              Calories
            </label>
            <input
              id="calories"
              min={1}
              type="number"
              required
              name="calories"
              className={styles.input}
              value={foodItem.calories}
              onChange={handleChange}
            />
          </div>

          {addNutrientsState && (
            <>
              <div className={styles.inputGroup}>
                <label className={styles.label} htmlFor="group">
                  Choose Nutrient To Add
                </label>
                <select
                  id="group"
                  name="group"
                  className={styles.input}
                  onChange={(e) => setAddNutrient(e.target.value)}
                  selected={addNutrient}
                >
                  <option value="">Select Nutrient</option>
                  {Object.keys(nutrientsCreationInfo).map((key, index) => (
                    <option key={index} value={nutrientsCreationInfo[key].name}>
                      {nutrientsCreationInfo[key].name}
                    </option>
                  ))}
                </select>
              </div>
              {addNutrient !== "" && (
                <div className={styles.inputGroup}>
                  <label className={styles.label} htmlFor="amount">
                    {nutrientsCreationInfo[addNutrient].name} /{" "}
                    {nutrientsCreationInfo[addNutrient].unit} / Amount
                  </label>
                  <input
                    id="amount"
                    min={0}
                    required
                    name="amount"
                    className={styles.input}
                    value={nutrientsCreationInfo[addNutrient].amount}
                    onChange={(e) =>
                      setNutrientsCreationInfo({
                        ...nutrientsCreationInfo,
                        [addNutrient]: {
                          ...nutrientsCreationInfo[addNutrient],
                          amount: e.target.value,
                        },
                      })
                    }
                  />
                </div>
              )}
            </>
          )}
          {!addNutrientsState && (
            <div className={styles.container}>
              {Object.keys(nutrientsCreationInfo).map((key, index) => (
                <div key={index} className={styles.nutrient}>
                  {nutrientsCreationInfo[key].amount > 0 && (
                    <p>
                      {nutrientsCreationInfo[key].name} /{" "}
                      {nutrientsCreationInfo[key].unit} /{" "}
                      {nutrientsCreationInfo[key].amount}
                    </p>
                  )}
                </div>
              ))}
            </div>
          )}
          <button className={styles.submitButton} type="submit">
            Create Food
          </button>
        </form>
      </div>
    </div>
  );
};
export default CreateCustomFood;
