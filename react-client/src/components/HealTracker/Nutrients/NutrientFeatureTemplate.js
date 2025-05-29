import { useEffect, useState } from "react";
import { useParams, useNavigate, Outlet } from "react-router-dom";

import * as PathCreator from "../../../util/PathCreator";
import styles from "./NutrientFeatureTemplate.module.css";

const NutrientFeatureTemplate = () => {
  const navigate = useNavigate();
  const [title, setTitle] = useState();
  const [activeTab, setActiveTab] = useState("");
  const { featureType } = useParams();

  useEffect(() => {
    setTitle(featureType.replace(/([A-Z])/g, " $1").trim());
  }, [featureType]);

  return (
    <div className={styles.body_container_main}>
      <h2 className={styles.body_container_section_h2}>{title}</h2>
      <div className={styles.tabs}>
        <button
          className={
            activeTab === "description" ? styles.activeTab : styles.tab
          }
          onClick={() => {
            setActiveTab("description");
            navigate(
              PathCreator.nutrientInfo("feature", featureType, "description")
            );
          }}
        >
          Description
        </button>
        {/* <button
          className={
            activeTab === "barCharStatistic" ? styles.activeTab : styles.tab
          }
          onClick={() => {
            setActiveTab("barCharStatistic");
            navigate(
              PathCreator.nutrientInfo(
                "feature",
                featureType,
                "barCharStatistic?sort=DO&limit=50"
              )
            );
          }}
        >
          Statistics
        </button> */}
      </div>

      <div className={styles.tabContent}>
        <Outlet />
      </div>
    </div>
  );
};

export default NutrientFeatureTemplate;
