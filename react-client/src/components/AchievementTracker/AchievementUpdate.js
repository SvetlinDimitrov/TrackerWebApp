import React, { useContext, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styles from "./AchievementUpdate.module.css";

import { NotificationContext } from "../../context/Notification";
import { AuthContext } from "../../context/UserCredentials";
import * as PathCreator from "../../util/PathCreator";
import api from "../../util/api";

const AchievementUpdate = () => {
  const navigate = useNavigate();
  const { achId } = useParams();
  const { setFailedMessage, setSuccessfulMessage } =
    useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;
  const [achievement, setAchievement] = useState({
    progress: 0,
    date: new Date().toISOString().split("T")[0],
    replace: false,
  });

  const handleUpdate = async () => {
    try {
      await api.patch(
        `/achievement/${achId}/addProgress?replaceDailyProgress=${achievement.replace}`,
        achievement,
        {
          headers: { Authorization: `Bearer ${userToken}` },
        }
      );
      setSuccessfulMessage({
        message: "Achievement updated successfully!",
        flag: true,
      });
    } catch (error) {
      if (error.response.status === 400) {
        setFailedMessage({
          message: error.response.data.message,
          flag: true,
        });
      } else {
        setFailedMessage({
          message: "Something went wrong. Please try again later.",
          flag: true,
        });
      }
    }
    navigate(PathCreator.achievementPathId(achId));
  };
  return (
    <div className={styles.modalBackground}>
      <button
        className={styles.closeButton}
        onClick={() => navigate(PathCreator.achievementPathId(achId))}
      >
        X
      </button>
      <div className={styles.modal}>
        <h1 className={styles.title}>Add Progress</h1>
        <div className={styles.inputGroup}>
          <label className={styles.label} htmlFor="progress">
            Progress
          </label>
          <input
            className={styles.input}
            type="number"
            id="progress"
            placeholder="Progress"
            min={0}
            required
            value={achievement.progress}
            onChange={(e) =>
              setAchievement((value) => ({
                ...value,
                progress: e.target.value,
              }))
            }
          />
        </div>
        <div className={styles.inputGroup}>
          <label className={styles.label} htmlFor="date">
            Date of Achievement
          </label>
          <input
            className={styles.input}
            type="date"
            id="date"
            required
            value={achievement.date}
            onChange={(e) =>
              setAchievement((value) => ({
                ...value,
                date: e.target.value,
              }))
            }
          />
        </div>
        <div className={styles.inputGroup}>
          <label className={styles.label} htmlFor="completed">
            Replace The Existing One
          </label>
          <select
            className={styles.input}
            id="completed"
            defaultValue={"false"}
            value={achievement.replace}
            onChange={(e) =>
              setAchievement((value) => ({
                ...value,
                replace: e.target.value,
              }))
            }
          >
            <option value="true">True</option>
            <option value="false">False</option>
          </select>
        </div>
        <div className={styles.buttonGroup}>
          <button
            type="submit"
            className={styles.button}
            onClick={() => handleUpdate()}
          >
            Submit
          </button>
        </div>
      </div>
    </div>
  );
};

export default AchievementUpdate;
