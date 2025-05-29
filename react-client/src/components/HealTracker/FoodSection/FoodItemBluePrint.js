import { faCheck } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useContext, useState } from "react";

import { NotificationContext } from "../../../context/Notification";
import { AuthContext } from "../../../context/UserCredentials";
import { Gauge2 } from "../../../util/Tools";
import api from "../../../util/api";
import styles from "./FoodItemBluePrint.module.css";

const FoodItemBluePrint = ({
  foodInfo,
  handleDeleteFood,
  handleAddFood,
  handleEditFood,
  onClose,
}) => {
  const [showVitamins, setShowVitamins] = useState(false);
  const [showMinerals, setShowMinerals] = useState(false);
  const [showMacros, setShowMacros] = useState(false);
  const [inputValue, setInputValue] = useState(foodInfo.size);
  const [food, setFood] = useState(foodInfo);
  const { user } = useContext(AuthContext);
  const { setFailedMessage } = useContext(NotificationContext);
  const userToken = user.tokenInfo.token;

  const changeFoodSize = async (e) => {
    e.preventDefault();

    let foodTypeConverted;
    if (food.foodClass === "FinalFood") {
      foodTypeConverted = "foundationFoods";
    } else if (food.foodClass === "Survey") {
      foodTypeConverted = "surveyFoods";
    } else if (food.foodClass === "Branded") {
      foodTypeConverted = "brandedFoods";
    }
    const url =
      foodTypeConverted === undefined
        ? `/food/calculate?amount=${inputValue}`
        : `/food/embedded/${foodTypeConverted}/calculate?amount=${inputValue}`;
    try {
      const response = await api.patch(url, food, {
        headers: { Authorization: `Bearer ${userToken}` },
      });
      setFood(response.data);
    } catch (error) {
      setFailedMessage({
        message: "Something went wrong with food size change!",
        flag: true,
      });
    }
  };

  if (!food) {
    return <div id="preloader"></div>;
  }
  return (
    <>
      <div className={styles.overlay}>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        <div className={styles.container}>
          <div className={styles.container_row}>
            <h3>{handleAddFood ? "Add Food" : "Edit Food"}</h3>
            <button
              className={styles.container_row_acceptButton}
              onClick={() =>
                handleAddFood ? handleAddFood(food) : handleEditFood(food)
              }
            >
              <FontAwesomeIcon icon={faCheck} />
            </button>
          </div>

          <div className={styles.container_row}>
            <span>{food.description}</span>
          </div>
          <div className={styles.container_row}>
            <span>{food.calories.name}</span>
            <span>
              {formatNumber(food.calories.amount)} {food.calories.unit}
            </span>
          </div>
          <div className={styles.container_gauge}>
            <Gauge2
              width={300}
              height={130}
              diameter={120}
              legendRectSize={15}
              legendSpacing={15}
              fat={findMacronutrientAmount(food, "Fat")}
              protein={findMacronutrientAmount(food, "Protein")}
              carbohydrates={findMacronutrientAmount(food, "Carbohydrate")}
            />
          </div>
          <div className={styles.container_row}>
            <div className={styles.container_row_changeSize}>
              <span>Serving Size In Grams</span>
              <form
                onSubmit={(e) => {
                  changeFoodSize(e);
                }}
              >
                <input
                  type="number"
                  min={1}
                  max={100000}
                  step={0.01}
                  value={inputValue}
                  onChange={(e) => setInputValue(e.target.value)}
                />
                <button type="submit">Change Size</button>
              </form>
            </div>
          </div>
          <div
            className={styles.container_container}
            onClick={() => setShowVitamins(!showVitamins)}
          >
            Vitamins Information
          </div>
          {showVitamins && (
            <>
              {food.vitaminNutrients.map((vitaminNutrient, index) => {
                return (
                  <div className={styles.container_row} key={index}>
                    <span>{vitaminNutrient.name}</span>
                    <span>
                      {formatNumber(vitaminNutrient.amount)}{" "}
                      {vitaminNutrient.unit}
                    </span>
                  </div>
                );
              })}
            </>
          )}
          <div
            className={styles.container_container}
            onClick={() => setShowMinerals(!showMinerals)}
          >
            Minerals Information
          </div>
          {showMinerals && (
            <>
              {food.mineralNutrients.map((vitaminNutrient, index) => {
                return (
                  <div className={styles.container_row} key={index}>
                    <span>{vitaminNutrient.name}</span>
                    <span>
                      {formatNumber(vitaminNutrient.amount)}{" "}
                      {vitaminNutrient.unit}
                    </span>
                  </div>
                );
              })}
            </>
          )}
          <div
            className={styles.container_container}
            onClick={() => setShowMacros(!showMacros)}
          >
            Macros Information
          </div>
          {showMacros && (
            <>
              {food.macroNutrients.map((vitaminNutrient, index) => {
                return (
                  <div className={styles.container_row} key={index}>
                    <span>{vitaminNutrient.name}</span>
                    <span>
                      {formatNumber(vitaminNutrient.amount)}{" "}
                      {vitaminNutrient.unit}
                    </span>
                  </div>
                );
              })}
            </>
          )}
          <div className={styles.container_specialRow}>
            {!handleAddFood && (
              <button
                className={styles.container_specialRow_DeleteButton}
                onClick={() => handleDeleteFood(food)}
              >
                Delete Food
              </button>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default FoodItemBluePrint;
function formatNumber(num) {
  if (Math.abs(Math.round(num) - num) < 0.01) {
    return num;
  } else {
    return num.toFixed(3);
  }
}
const findMacronutrientAmount = (food, name) => {
  const result = food.macroNutrients.find((macro) => macro.name === name);
  return result ? result.amount : 0;
};
