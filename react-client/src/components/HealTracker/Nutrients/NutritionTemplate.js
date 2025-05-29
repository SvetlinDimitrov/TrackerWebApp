import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams, Outlet } from "react-router-dom";

import { NutrientContext } from "../../../context/NutrientContextProvider";
import * as PathCreator from "../../../util/PathCreator";
import styles from "./NutritionTemplate.module.css";

const NutritionTemplate = () => {
  let { nutrition, nutritionType } = useParams();
  const navigate = useNavigate();
  const { setNutrient, nutrient } = useContext(NutrientContext);
  const [activeTab, setActiveTab] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      if (nutrition === "vitamin") {
        const vitamins = await fetch("http://localhost:3001/data/vitamin.json");
        const vitaminsData = await vitamins.json();
        setNutrient(filterArray(vitaminsData, nutritionType));
      } else if (nutrition === "mineral") {
        const minerals = await fetch(
          "http://localhost:3001/data/minerals.json"
        );
        const mineralsData = await minerals.json();
        setNutrient(filterArray(mineralsData, nutritionType));
      } else if (nutrition === "macronutrient") {
        const macroNutrients = await fetch(
          "http://localhost:3001/data/macronutrients.json"
        );
        const macroNutrientsData = await macroNutrients.json();
        setNutrient(filterArray(macroNutrientsData, nutritionType));
      } else if (nutrition === "macronutrientTypes") {
        const macroNutrientTypes = await fetch(
          "http://localhost:3001/data/macronutrientTypes.json"
        );
        const macroNutrientTypesData = await macroNutrientTypes.json();
        setNutrient(filterArray(macroNutrientTypesData, nutritionType));
      }
    };

    fetchData();
  }, [navigate, nutrition, nutritionType, setNutrient]);

  if (!nutrient) {
    return <div id="preloader"></div>;
  }

  return (
    <div className={styles.body_container_main}>
      <h2 className={styles.body_container_section_h2}>{`${nutrient.name}`}</h2>
      <div className={styles.tabs}>
        <button
          className={
            activeTab === "description" ? styles.activeTab : styles.tab
          }
          onClick={() => {
            setActiveTab("description");
            navigate(
              PathCreator.nutrientInfo(nutrition, nutritionType, "description")
            );
          }}
        >
          Description
        </button>
        <button
          className={activeTab === "functions" ? styles.activeTab : styles.tab}
          onClick={() => {
            setActiveTab("functions");
            navigate(
              PathCreator.nutrientInfo(nutrition, nutritionType, "functions")
            );
          }}
        >
          Functions
        </button>

        <button
          className={activeTab === "sources" ? styles.activeTab : styles.tab}
          onClick={() => {
            setActiveTab("sources");
            navigate(
              PathCreator.nutrientInfo(nutrition, nutritionType, "sources")
            );
          }}
        >
          Sources
        </button>
        {nutrition === "macronutrientTypes" && (
          <button
            className={
              activeTab === "healthConsiderations"
                ? styles.activeTab
                : styles.tab
            }
            onClick={() => {
              setActiveTab("healthConsiderations");
              navigate(
                PathCreator.nutrientInfo(
                  nutrition,
                  nutritionType,
                  "healthConsiderations"
                )
              );
            }}
          >
            Health Considerations
          </button>
        )}
        {nutrition === "macronutrient" && nutritionType !== "Protein" && (
          <button
            className={activeTab === "types" ? styles.activeTab : styles.tab}
            onClick={() => {
              setActiveTab("types");
              navigate(
                PathCreator.nutrientInfo(nutrition, nutritionType, "types")
              );
            }}
          >
            Types
          </button>
        )}
        {nutrition === "macronutrient" && (
          <button
            className={
              activeTab === "dietaryConsiderations"
                ? styles.activeTab
                : styles.tab
            }
            onClick={() => {
              setActiveTab("dietaryConsiderations");
              navigate(
                PathCreator.nutrientInfo(
                  nutrition,
                  nutritionType,
                  "dietaryConsiderations"
                )
              );
            }}
          >
            Dietary Considerations
          </button>
        )}
        {nutrition !== "macronutrient" && (
          <button
            className={activeTab === "intake" ? styles.activeTab : styles.tab}
            onClick={() => {
              setActiveTab("intake");
              navigate(
                PathCreator.nutrientInfo(nutrition, nutritionType, "intake")
              );
            }}
          >
            Daily Intake
          </button>
        )}
        <button
          className={
            activeTab === "barCharStatistic" ? styles.activeTab : styles.tab
          }
          onClick={() => {
            setActiveTab("barCharStatistic");
            navigate(
              PathCreator.nutrientInfo(
                nutrition,
                nutritionType,
                "barCharStatistic?sort=DO&limit=50&min=0&max=100&type=finalFoods"
              )
            );
          }}
        >
          Statistics
        </button>
      </div>

      <div className={styles.tabContent}>
        <Outlet />
      </div>
    </div>
  );
};

export default NutritionTemplate;

const filterArray = (data, name) => {
  const filtered = data.filter((item) => item.name === name);
  return filtered[0];
};
