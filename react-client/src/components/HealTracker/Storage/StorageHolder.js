import React, { useContext, useEffect, useState } from "react";
import { useNavigate, useParams, Outlet } from "react-router-dom";

import * as PathCreator from "../../../util/PathCreator";
import { NotificationContext } from "../../../context/Notification";
import { AuthContext } from "../../../context/UserCredentials";
import api from "../../../util/api";
import styles from "./StorageHolder.module.css";

const StorageHolder = () => {
  const navigate = useNavigate();
  const { recordId, storageId } = useParams();
  const { setFailedMessage, setSuccessfulMessage } =
    useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;

  const [storage, setStorage] = useState(undefined);

  useEffect(() => {
    setStorage(undefined);
    const fetchData = async () => {
      try {
        const response = await api.get(
          `/storage?recordId=${recordId}&storageId=${storageId}`,
          {
            headers: { Authorization: `Bearer ${userToken}` },
          }
        );
        setStorage(response.data);
      } catch (error) {
        setFailedMessage({
          message:
            "Something went wrong with storage data. Please try again later!",
          flag: true,
        });
        navigate(PathCreator.recordPath(recordId));
        return;
      }
    };
    if (storageId) {
      fetchData();
    }
  }, [recordId, navigate, setFailedMessage, userToken, storageId]);

  const onDelete = async () => {
    if (
      window.confirm("Are you sure you want to delete this storage?") === false
    )
      return;
    try {
      await api.delete(
        `/storage/delete/${storageId}/record?recordId=${recordId}`,
        {
          headers: { Authorization: `Bearer ${userToken}` },
        }
      );
      setSuccessfulMessage({
        message: "Storage " + storage.name + " was successful!",
        flag: true,
      });
    } catch (error) {
      setFailedMessage({
        message:
          "Something went wrong with storage deletion. Please try again later!",
        flag: true,
      });
    }
    navigate(PathCreator.recordPath(recordId));
  };
  return (
    <>
      <div className={styles.container}>
        <h1 className={styles.title}>Storage Menu</h1>
        {storage && (
          <p className={styles.storageInfo}>Selected Storage {storage.name} </p>
        )}
        <div className={styles.options}>
          <button
            className={styles.optionButton}
            onClick={() =>
              navigate(PathCreator.recordPath(recordId) + "/selectStorage")
            }
          >
            Select Storage
          </button>
          <button
            className={styles.optionButton}
            onClick={() =>
              navigate(PathCreator.recordPath(recordId) + "/createStorage")
            }
          >
            Create Storage
          </button>
          {storage && (
            <button className={styles.optionButton} onClick={() => onDelete()}>
              Delete Storage
            </button>
          )}
        </div>
      </div>
      <Outlet />
    </>
  );
};

export default StorageHolder;
