import { useContext } from "react";

import { NutrientContext } from "../../../../context/NutrientContextProvider";
import styles from "../NutritionTemplate.module.css";

const NutrientTemplateDescription = () => {
  const { nutrient } = useContext(NutrientContext);

  return (
    <div className={styles.body_container_section}>
      <p className={styles.body_container_description}>
        {" "}
        {nutrient.description}
      </p>
    </div>
  );
};
export default NutrientTemplateDescription;
