import { faMinus, faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";

import { calculatedPrecentedValues } from "../../../util/RecordUtils";

import styles from "./DetailsOfRecord.module.css";

import { Gauge, PipeChar, Gauge2, AnimatedNumber } from "../../../util/Tools";

const DetailsOfRecord = ({ record }) => {
  const [categoryShow, setCategoryShow] = useState(undefined);
  const [show, setShow] = useState("");
  const types = ["Vitamin", "Mineral", "Macronutrient", "Calories"];
  const categories = ["Vitamin", "Mineral", "Macronutrient", "Calories"];

  const handleOnClick = (name) => {
    setShow((prevShow) => (prevShow === name ? "" : name));
  };

  if (record === undefined) {
    return <div id="preloader"></div>;
  }

  return (
    <>
      <div className={styles.container}>
        <div>
          <h1 className={styles.container_h1}>Record: {record.name}</h1>
          <div className={styles.container_gauge}>
            {types.map((d, i) => (
              <Gauge
                key={i}
                className={`pathEffect${i}`}
                width={250}
                height={270}
                type={d}
                data={record}
              />
            ))}
          </div>
          <p className={styles.container_p}>
            Clicking one of the following categories will give you detailed
          </p>
          <div className={styles.container_gauge}>
            {categories.map((category) => (
              <div
                key={category}
                className={styles.container_gauge_category}
                onClick={() =>
                  setCategoryShow((s) => (s === category ? "" : category))
                }
              >
                {category}
              </div>
            ))}
          </div>
          {categoryShow && (
            <div className={styles.container_info}>
              {calculatedPrecentedValues(record, categoryShow).map((d) => {
                return (
                  <>
                    <div
                      key={d.id}
                      className={styles.container_info_info}
                      onClick={() => handleOnClick(d.name)}
                    >
                      <span>{d.name.replace(/([A-Z])/g, " $1").trim()}</span>
                      <span>
                        Completed:{" "}
                        <AnimatedNumber
                          value={isNaN(d.precented) ? 0 : d.precented.toFixed(2)}
                        />
                        %
                      </span>
                      <FontAwesomeIcon
                        icon={show === d.name ? faMinus : faPlus}
                      />
                    </div>
                    {show === d.name && (
                      <>
                        <div className={styles.container_info_info_info}>
                          <PipeChar width={300} height={30} data={d} />
                        </div>

                        {show === d.name && d.type === "Calories" && (
                          <div className={styles.container_info_info_info}>
                            <Gauge2
                              width={260}
                              height={120}
                              diameter={100}
                              legendRectSize={12}
                              legendSpacing={10}
                              protein={d.protein}
                              fat={d.fat}
                              carbohydrates={d.carbohydrates}
                            />
                          </div>
                        )}
                      </>
                    )}
                  </>
                );
              })}
            </div>
          )}
        </div>
      </div>
    </>
  );
};

export default DetailsOfRecord;
