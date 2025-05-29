import React, { useState } from "react";
import styles from "./Tooltip.module.css";

const Tooltip = ({ children, type }) => {
  const [show, setShow] = useState(false);

  return (
    <div
      className={styles.tooltip}
      onMouseEnter={() => setShow(true)}
      onMouseLeave={() => setShow(false)}
    >
      {children}
      {show && (
        <div className={styles.tooltipContent}>
          {type !== "Daily" && (
            <div>
              <span
                className={styles.colorSquare}
                style={{ backgroundColor: "steelblue" }}
              ></span>
              Best Progress
            </div>
          )}
          {type !== "Daily" && (
            <div>
              <span
                className={styles.colorSquare}
                style={{ backgroundColor: "green" }}
              ></span>
              Worst Progress
            </div>
          )}
          {type !== "Daily" && (
            <div>
              <span
                className={styles.colorSquare}
                style={{ backgroundColor: "red" }}
              ></span>
              Total Progress
            </div>
          )}

          {type !== "Daily" && (
            <div>
              <span
                className={styles.colorSquare}
                style={{ backgroundColor: "purple" }}
              ></span>
              Average Progress
            </div>
          )}

          <div>
            <span>DO: Descending Order</span>
          </div>
          <div>
            <span>AO: Ascending Order</span>
          </div>
        </div>
      )}
    </div>
  );
};

export default Tooltip;
