import React, { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./SelectRecord.module.css";

import * as PathCreator from "../../../util/PathCreator";
import { NotificationContext } from "../../../context/Notification";
import { AuthContext } from "../../../context/UserCredentials";
import api from "../../../util/api";

const SelectRecord = () => {
  const navigate = useNavigate();
  const { setFailedMessage } = useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;

  const [records, setRecords] = useState(undefined);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await api.get("/record/all", {
          headers: { Authorization: `Bearer ${userToken}` },
        });
        setRecords(response.data);
      } catch (error) {
        setFailedMessage({
          message:
            "Something went wrong with record creation. Please try again later!",
          flag: true,
        });
        navigate("/");
      }
    };
    fetchData();
  }, [navigate, setFailedMessage, userToken]);

  if (records === undefined) {
    return <div id="preloader"></div>;
  }

  const onClose = () => {
    navigate(PathCreator.basicPath());
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.popup}>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        <h2 className={styles.header}>Select Record</h2>
        <select
          onChange={(e) => navigate(PathCreator.recordPath(e.target.value))}
          className={styles.select}
        >
          <option value={-1}>Choose Storage</option>
          {records.map((record) => (
            <option key={record.id} value={record.id}>
              {record.name}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
};

export default SelectRecord;
