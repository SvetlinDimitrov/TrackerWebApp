import { useContext } from "react";

import { NutrientContext } from "../../../../context/NutrientContextProvider";
import styles from "../NutritionTemplate.module.css";

const NutritionTemplateIntake = () => {
  const { nutrient } = useContext(NutrientContext);

  return (
    <div className={styles.body_container_section}>
      <ul className={styles.body_container_section_sources}>
        <li>
          <strong>Males:</strong> ranges from{" "}
          {`${nutrient.maleLowerBoundIntake} to ${nutrient.maleHigherBoundIntake} ${nutrient.measure}.`}
        </li>
        <li>
          <strong>Females:</strong> ranges from{" "}
          {`${nutrient.femaleLowerBoundIntake} to ${nutrient.femaleHigherBoundIntake} ${nutrient.measure}.`}
        </li>
      </ul>
    </div>
  );
};
export default NutritionTemplateIntake;
