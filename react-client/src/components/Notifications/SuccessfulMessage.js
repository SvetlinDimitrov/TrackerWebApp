import React from "react";
import styles from "./SuccessfulMessage.module.css";

const SuccessMessage = ({ onClose, message }) => {
  return (
    <div className={styles.overlay}>
      <div className={styles.successMessage}>
        <p>{message}</p>
        <button className={styles.successMessageButton} onClick={onClose}>
          Close
        </button>
      </div>
    </div>
  );
};

export default SuccessMessage;
