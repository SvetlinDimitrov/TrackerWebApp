import React, { useContext, useEffect, useState } from "react";
import { Outlet, useNavigate, useParams } from "react-router-dom";

import styles from "./AchievementInfo.module.css";
import { NotificationContext } from "../../context/Notification";
import { AuthContext } from "../../context/UserCredentials";
import * as PathCreator from "../../util/PathCreator";

import api from "../../util/api";

const AchievementInfo = () => {
  const navigate = useNavigate();
  const { achId } = useParams();
  const { setFailedMessage } = useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;
  const [achievement, setAchievement] = useState(undefined);

  useEffect(() => {
    setAchievement(undefined);
    const fetchData = async () => {
      try {
        const response = await api.get(`/achievement?id=${achId}`, {
          headers: { Authorization: `Bearer ${userToken}` },
        });
        setAchievement(response.data);
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
  }, [achId, navigate, setFailedMessage, userToken]);

  if (achievement === undefined) {
    return <div className="preloader"></div>;
  }

  return (
    <>
      <div className={styles.body}>
        <div className={styles.container}>
          <div className={styles.titleRow}>
            <h1 className={styles.title}>{achievement.name}</h1>
            <p className={styles.date}>Started on {achievement.startDate}</p>
          </div>
          <div className={styles.row}>
            <h2 className={styles.subtitle}>{achievement.description}</h2>
            <button
              className={styles.updateButton}
              onClick={() =>
                navigate(PathCreator.achievementPathId(achId) + "/addProgress")
              }
            >
              Update Progress
            </button>
          </div>
          <p className={styles.goalText}>
            Goal {achievement.goal} {achievement.measurement}
          </p>
          <div className={styles.reports}>
            <h3 className={styles.reportsH3}>Reports</h3>
            <div className={styles.reportOptions}>
              <button
                onClick={() =>
                  navigate(
                    PathCreator.achievementPathIdReport(
                      achId,
                      "Daily",
                      "date_AO"
                    )
                  )
                }
              >
                Daily
              </button>
              <button
                onClick={() =>
                  navigate(
                    PathCreator.achievementPathIdReport(
                      achId,
                      "Weekly",
                      "date_AO"
                    )
                  )
                }
              >
                Weekly
              </button>
              <button
                onClick={() =>
                  navigate(
                    PathCreator.achievementPathIdReport(
                      achId,
                      "Monthly",
                      "date_AO"
                    )
                  )
                }
              >
                Monthly
              </button>
              <button
                onClick={() =>
                  navigate(
                    PathCreator.achievementPathIdReport(
                      achId,
                      "Yearly",
                      "date_AO"
                    )
                  )
                }
              >
                Yearly
              </button>
            </div>
          </div>
        </div>
        <Outlet achievement={achievement} />
      </div>
    </>
  );
};

export default AchievementInfo;
