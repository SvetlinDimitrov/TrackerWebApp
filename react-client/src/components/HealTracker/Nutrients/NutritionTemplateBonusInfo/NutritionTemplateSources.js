import { useContext } from "react";

import { NutrientContext } from "../../../../context/NutrientContextProvider";
import styles from "../NutritionTemplate.module.css";

const NutritionTemplateSources = () => {
  const { nutrient } = useContext(NutrientContext);

  return (
    <div className={styles.body_container_section}>
      <ul className={styles.body_container_section_sources}>
        {nutrient.sources.map((func, index) => (
          <li key={index}>
            {func.key && func.key !== "-" && func.key !== "" ? (
              <>
                <strong>{func.key}:</strong> {func.value}
              </>
            ) : (
              <>
                <strong>{func.value}</strong>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};
export default NutritionTemplateSources;
