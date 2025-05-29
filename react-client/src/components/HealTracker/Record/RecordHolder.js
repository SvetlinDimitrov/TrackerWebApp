import React, { useEffect, useState, useContext } from "react";
import styles from "./RecordHolder.module.css";
import { Outlet, useNavigate, useParams, useLocation } from "react-router-dom";

import * as PathCreator from "../../../util/PathCreator";
import DetailsOfRecord from "./DetailsOfRecord";
import { NotificationContext } from "../../../context/Notification";
import { AuthContext } from "../../../context/UserCredentials";
import api from "../../../util/api";
import AlertMessage from "../../Notifications/AlertMessage";

function RecordHolder() {
  const navigate = useNavigate();
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const detailRecord = searchParams.get("detailRecord") === "true";
  const { recordId, storageId } = useParams();
  const { setFailedMessage } = useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;

  const [record, setRecord] = useState(undefined);

  useEffect(() => {
    setRecord(undefined);
    const fetchData = async () => {
      try {
        const response = await api.get("/record/" + recordId, {
          headers: { Authorization: `Bearer ${userToken}` },
        });
        setRecord(response.data);
      } catch (error) {
        setFailedMessage({
          message:
            "Something went wrong with record creation. Please try again later!",
          flag: true,
        });
        navigate("/");
      }
    };
    if (recordId) {
      fetchData();
    }
  }, [recordId, navigate, setFailedMessage, userToken]);

  return (
    <>
      <AlertMessage />
      <div className={styles.container}>
        <h1 className={styles.title}>Record Menu</h1>
        {record && (
          <>
            <p className={styles.recordInfo}>Selected Record {record.name} / Calories {record.dailyCaloriesToConsume.toFixed(2)} kcal</p>
          </>
        )}
        <div className={styles.options}>
          <button
            className={styles.optionButton}
            onClick={() => navigate(PathCreator.basicPath() + "/selectRecord")}
          >
            Select Record
          </button>
          <button
            className={styles.optionButton}
            onClick={() => navigate(PathCreator.basicPath() + "/createRecord")}
          >
            Create Record
          </button>
          {record && (
            <button
              className={styles.optionButton}
              onClick={() =>
                navigate(PathCreator.recordPath(recordId) + "/deleteRecord")
              }
            >
              Delete Record
            </button>
          )}
          {record && (
            <button
              className={styles.optionButton}
              onClick={() => {
                navigate(
                  storageId
                    ? PathCreator.storagePath(recordId, storageId) +
                        "?detailRecord=" +
                        !detailRecord
                    : PathCreator.recordPath(recordId) +
                        "?detailRecord=" +
                        !detailRecord
                );
              }}
            >
              {detailRecord ? "Hide Details" : "More Details"}
            </button>
          )}
        </div>
      </div>
      <Outlet />
      {detailRecord === true && <DetailsOfRecord record={record} />}
    </>
  );
}

export default RecordHolder;
