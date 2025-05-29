import React, { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./SelectAchievement.module.css";

import * as PathCreator from "../../util/PathCreator";
import { NotificationContext } from "../../context/Notification";
import { AuthContext } from "../../context/UserCredentials";
import api from "../../util/api";

const SelectAchievement = () => {
  const navigate = useNavigate();
  const { setFailedMessage } = useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;

  const [achievements, setAchievements] = useState(undefined);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await api.get("/achievement/all", {
          headers: { Authorization: `Bearer ${userToken}` },
        });
        setAchievements(response.data);
      } catch (error) {
        setFailedMessage({
          message:
            "Something went wrong with achievement fetching. Please try again later!",
          flag: true,
        });
        navigate(PathCreator.basicAchievementPath());
      }
    };
    fetchData();
  }, [navigate, setFailedMessage, userToken]);

  if (achievements === undefined) {
    return <div id="preloader"></div>;
  }
  return (
    <div className={styles.overlay}>
      <div className={styles.popup}>
        <button
          className={styles.closeButton}
          onClick={() => navigate(PathCreator.basicAchievementPath())}
        >
          X
        </button>
        <h2 className={styles.header}>Select Achievements</h2>
        <select
          onChange={(e) =>
            navigate(PathCreator.achievementPathId(e.target.value))
          }
          className={styles.select}
        >
          <option value={-1}>Choose Achievement</option>
          {achievements.map((ach) => (
            <option key={ach.id} value={ach.id}>
              {ach.name}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
};
export default SelectAchievement;
