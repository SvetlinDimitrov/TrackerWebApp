import styles from "./FailedMessage.module.css";

const FailedMessage = ({ onClose, message }) => {
  return (
    <div className={styles.overlay}>
      <div className={styles.failedMessage}>
        <p>{message}</p>
        <button className={styles.failedMessageButton} onClick={onClose}>
          Close
        </button>
      </div>
    </div>
  );
};

export default FailedMessage;
