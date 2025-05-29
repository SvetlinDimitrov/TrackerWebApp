import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";

import * as PathCreator from "../../../util/PathCreator";
import api from "../../../util/api";
import styles from "./CreateRecord.module.css";
import { NotificationContext } from "../../../context/Notification";
import { AuthContext } from "../../../context/UserCredentials";

const CreateRecord = () => {
  const navigate = useNavigate();
  const { setFailedMessage, setSuccessfulMessage } =
    useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;
  const [recordName, setRecordName] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setRecordName(recordName.trim());

    try {
      await api.post(
        `/record?name=${encodeURIComponent(recordName)}`,
        {},
        { headers: { Authorization: `Bearer ${userToken}` } }
      );
      setSuccessfulMessage({
        message: "Record created successfully!",
        flag: true,
      });
    } catch (error) {
      setFailedMessage({
        message:
          "Something went wrong with record creation. Please try again later!",
        flag: true,
      });
    }
    setRecordName("");
    navigate(PathCreator.basicPath());
  };
  const onClose = () => {
    navigate(PathCreator.basicPath());
  };
  return (
    <div className={styles.overlay}>
      <div className={styles.popup}>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        <form onSubmit={handleSubmit} className={styles.form}>
          <h2 className={styles.header}>Create Record</h2>
          <input
            type="text"
            value={recordName}
            minLength={3}
            onChange={(e) => setRecordName(e.target.value)}
            className={styles.input}
            placeholder="Record Name"
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

export default CreateRecord;
