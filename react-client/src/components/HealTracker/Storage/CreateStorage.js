import React, { useState, useContext } from "react";
import { useNavigate, useParams, useLocation } from "react-router-dom";

import * as PathCreator from "../../../util/PathCreator";
import { NotificationContext } from "../../../context/Notification";
import { AuthContext } from "../../../context/UserCredentials";
import api from "../../../util/api";
import styles from "./CreateStorage.module.css";

const CreateStorage = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const detailRecord = queryParams.get("detailsRecord") === "true";
  const { recordId } = useParams();
  const navigate = useNavigate();
  const [storageName, setStorageName] = useState("");
  const { setFailedMessage, setSuccessfulMessage } =
    useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post(
        `/storage?storageName=${encodeURIComponent(
          storageName
        )}&recordId=${recordId}`,
        {},
        { headers: { Authorization: `Bearer ${userToken}` } }
      );
      setSuccessfulMessage({
        message: "Storage: " + storageName + " was created successfully!",
        flag: true,
      });
    } catch (error) {
      setFailedMessage({
        message:
          "Something went wrong with record creation. Please try again later!",
        flag: true,
      });
    }
    setStorageName("");
    navigate(PathCreator.recordPath(recordId, detailRecord));
  };

  const onClose = () => {
    navigate(PathCreator.recordPath(recordId, detailRecord));
  };
  return (
    <div className={styles.overlay}>
      <div className={styles.popup}>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        <form onSubmit={handleSubmit} className={styles.form}>
          <h2 className={styles.header}>Create Storage</h2>
          <input
            type="text"
            value={storageName}
            minLength={3}
            onChange={(e) => setStorageName(e.target.value)}
            className={styles.input}
            placeholder="Storage Name"
            required
          />
          <button type="submit" className={styles.button}>
            Create
          </button>
        </form>
      </div>
    </div>
  );
};

export default CreateStorage;
