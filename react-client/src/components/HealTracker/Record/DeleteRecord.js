import React, { useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";

import * as PathCreator from "../../../util/PathCreator";
import api from "../../../util/api";
import { NotificationContext } from "../../../context/Notification";
import { AuthContext } from "../../../context/UserCredentials";
import styles from "./DeleteRecord.module.css";

const DeleteRecord = () => {
  const { setFailedMessage, setSuccessfulMessage } =
    useContext(NotificationContext);
  const { recordId } = useParams();
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;
  const navigate = useNavigate();

  const handleDeletion = async () => {
    const confirmation = window.confirm(
      "Are you sure you want to delete this record?"
    );
    if (confirmation) {
      try {
        await api.delete(`/record/${recordId}`, {
          headers: { Authorization: `Bearer ${userToken}` },
        });

        setSuccessfulMessage({
          message: "Record created successfully!",
          flag: true,
        });
      } catch (error) {
        setFailedMessage({
          message:
            "Something went wrong with record deletion. Please try again later!",
          flag: true,
        });
      }
    }
    navigate("/health-tracker");
  };
  const onClose = () => {
    navigate(PathCreator.basicPath());
  };
  return (
    <div className={styles.overlay}>
      <div className={styles.container}>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        <h2 className={styles.container_header}>Delete Record</h2>
        <button
          className={styles.container_deleteButton}
          onClick={() => handleDeletion()}
        >
          Delete Record
        </button>
      </div>
    </div>
  );
};

export default DeleteRecord;
