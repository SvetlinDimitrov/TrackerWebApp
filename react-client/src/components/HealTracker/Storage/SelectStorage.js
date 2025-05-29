import React, { useState, useEffect, useContext } from "react";
import { useNavigate, useParams, useLocation } from "react-router-dom";

import * as PathCreator from "../../../util/PathCreator";
import { NotificationContext } from "../../../context/Notification";
import { AuthContext } from "../../../context/UserCredentials";
import api from "../../../util/api";
import styles from "./SelectStorage.module.css";

const SelectStorage = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const detailRecord = queryParams.get("detailsRecord") === "true";
  const [storages, setStorages] = useState();
  const { recordId } = useParams();
  const { setFailedMessage } = useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await api.get(`/storage/all?recordId=${recordId}`, {
          headers: { Authorization: `Bearer ${userToken}` },
        });
        setStorages(response.data);
      } catch (error) {
        setFailedMessage({
          message:
            "Something went wrong with record creation. Please try again later!",
          flag: true,
        });
        navigate(PathCreator.recordPath(recordId, detailRecord));
        return;
      }
    };

    fetchData();
  }, [recordId, navigate, setFailedMessage, userToken, detailRecord]);

  if (storages === undefined) {
    return <div id="preloader"></div>;
  }
  const onClose = () => {
    navigate(PathCreator.recordPath(recordId, detailRecord));
  };
  return (
    <div className={styles.overlay}>
      <div className={styles.popup}>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        <h2 className={styles.header}>Select Storage</h2>
        <select
          onChange={(e) =>
            navigate(
              PathCreator.storagePath(recordId, e.target.value, detailRecord)
            )
          }
          className={styles.select}
        >
          <option value={-1}>Choose Storage</option>
          {storages.map((storage) => (
            <option key={storage.id} value={storage.id}>
              {storage.name}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
};

export default SelectStorage;
