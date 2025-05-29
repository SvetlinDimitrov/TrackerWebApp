import React, { useEffect, useState, useContext } from "react";
import styles from "./AchievementTracker.module.css";
import { Outlet, useNavigate, useParams } from "react-router-dom";

import * as PathCreator from "../../util/PathCreator";
import { NotificationContext } from "../../context/Notification";
import { AuthContext } from "../../context/UserCredentials";
import { AchievementContext } from "../../context/AchievementContextProvider";
import api from "../../util/api";
import AlertMessage from "../Notifications/AlertMessage";

const AchievementTracker = () => {
  const navigate = useNavigate();
  const { achId } = useParams();
  const { setFailedMessage, setSuccessfulMessage } =
    useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const { setAchievementToPass } = useContext(AchievementContext);
  const userToken = user.tokenInfo.token;
  const [achievement, setAchievement] = useState(undefined);

  useEffect(() => {
    setAchievement(undefined);
    setAchievementToPass(undefined);
    const fetchData = async () => {
      try {
        const response = await api.get(`/achievement?id=${achId}`, {
          headers: { Authorization: `Bearer ${userToken}` },
        });
        setAchievement(response.data);
        setAchievementToPass(response.data);
      } catch (error) {
        setFailedMessage({
          message:
            "Something went wrong with achievement fetching . Please try again later!",
          flag: true,
        });
        navigate("/");
      }
    };
    if (achId) {
      fetchData();
    }
  }, [achId, navigate, setFailedMessage, userToken, setAchievementToPass]);

  const handleDeleteAchievement = async (id) => {
    if (!window.confirm("Are you sure you want to delete this achievement?")) {
      return;
    }
    try {
      await api.delete("/achievement?id=" + id, {
        headers: { Authorization: `Bearer ${userToken}` },
      });
      setSuccessfulMessage({
        message: "Achievement deleted successfully!",
        flag: true,
      });
    } catch (error) {
      setFailedMessage({
        message:
          "Something went wrong with achievement deleting . Please try again later!",
        flag: true,
      });
    }
    navigate(PathCreator.basicAchievementPath());
  };
  return (
    <>
      <AlertMessage />
      <div className={styles.container}>
        <h1 className={styles.title}>Achievement Menu</h1>
        <div className={styles.options}>
          <button
            className={styles.optionButton}
            onClick={() =>
              navigate(PathCreator.basicAchievementPath() + "/select")
            }
          >
            Select Achievement
          </button>
          <button
            className={styles.optionButton}
            onClick={() =>
              navigate(PathCreator.basicAchievementPath() + "/create")
            }
          >
            Create Achievement
          </button>
          {achievement && (
            <button
              className={styles.optionButton}
              onClick={() => handleDeleteAchievement(achievement.id)}
            >
              Delete Achievement
            </button>
          )}
        </div>
      </div>
      <Outlet />
    </>
  );
};

export default AchievementTracker;
