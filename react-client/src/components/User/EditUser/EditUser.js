import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { NotificationContext } from "../../../context/Notification";
import { AuthContext } from "../../../context/UserCredentials";
import { validatedUserToken } from "../../../util/UserUtils";

import api from "../../../util/api";
import styles from "./EditUser.module.css";

const EditUser = () => {
  const { user, loginUser } = useContext(AuthContext);
  const { setSuccessfulMessage, setFailedMessage } =
    useContext(NotificationContext);
  const [currentUser, setCurrentUser] = useState(user.userData);
  const navigate = useNavigate();
  const validatedUser = validatedUserToken(user);

  const handleSubmit = async (e) => {
    await e.preventDefault();

    try {
      const responseUserToken = await api.patch("/auth/edit", currentUser, {
        headers: { Authorization: `Bearer ${user.tokenInfo.token}` },
      });

      const newUserTokenInfo = responseUserToken.data;

      const userDataResponse = await api.get("/auth/details", {
        headers: { Authorization: `Bearer ${newUserTokenInfo.token}` },
      });

      const userData = {
        userData: userDataResponse.data,
        tokenInfo: newUserTokenInfo,
      };

      loginUser(userData);

      await setSuccessfulMessage({
        message: "Changes were made successfully!",
        flag: true,
      });

      navigate("/");
    } catch (error) {
      setFailedMessage({
        message: "Something went wrong try again later!",
        flag: true,
      });
      navigate("/");
    }
  };

  if (validatedUser === "dateExpired") {
    setFailedMessage({
      message: "Your token has expired please login again!",
      flag: true,
    });
    navigate("/logout");
    return;
  }
  return (
    <>
      {currentUser !== undefined ? (
        <div className={styles.body}>
          <div className={styles.container}>
            <h1 className={styles.title}>Edit User Settings</h1>
            <form onSubmit={handleSubmit} method="POST">
              <div className={styles.formGroup}>
                <label htmlFor="username">Username</label>
                <input
                  type="text"
                  id="username"
                  name="username"
                  minLength="4"
                  required
                  value={currentUser.username ? currentUser.username : ""}
                  onChange={(e) =>
                    setCurrentUser({ ...currentUser, username: e.target.value })
                  }
                />
              </div>

              <div className={styles.formGroup}>
                <label htmlFor="kilograms">Weight (kg)</label>
                <input
                  type="number"
                  id="kilograms"
                  name="kilograms"
                  step="0.01"
                  min="5"
                  max={1000000}
                  value={+currentUser.kilograms || ""}
                  onChange={(e) =>
                    setCurrentUser({
                      ...currentUser,
                      kilograms: e.target.value,
                    })
                  }
                />
              </div>
              <div className={styles.formGroup}>
                <label htmlFor="height">Height (cm)</label>
                <input
                  type="number"
                  id="height"
                  name="height"
                  step="0.01"
                  min="0.5"
                  max={1000000}
                  value={+currentUser.height || ""}
                  onChange={(e) =>
                    setCurrentUser({ ...currentUser, height: e.target.value })
                  }
                />
              </div>
              <div className={styles.formGroup}>
                <label htmlFor="workoutState">Workout State</label>
                <select
                  id="workoutState"
                  name="workoutState"
                  value={
                    currentUser.workoutState ? currentUser.workoutState : ""
                  }
                  onChange={(e) =>
                    setCurrentUser({
                      ...currentUser,
                      workoutState:
                        e.target.value === ""
                          ? currentUser.workoutState
                          : e.target.value,
                    })
                  }
                >
                  <option value="">Choose Workout State</option>
                  <option value="SEDENTARY">Sedentary</option>
                  <option value="LIGHTLY_ACTIVE">Lightly Active</option>
                  <option value="MODERATELY_ACTIVE">Moderately Active</option>
                  <option value="VERY_ACTIVE">Very Active</option>
                  <option value="SUPER_ACTIVE">Super Active</option>
                </select>
              </div>
              <div className={styles.formGroup}>
                <label htmlFor="gender">Gender</label>
                <select
                  id="gender"
                  name="gender"
                  value={currentUser.gender ? currentUser.gender : ""}
                  onChange={(e) =>
                    setCurrentUser({
                      ...currentUser,
                      gender:
                        e.target.value === ""
                          ? currentUser.gender
                          : e.target.value,
                    })
                  }
                >
                  <option value="">Choose Gender</option>
                  <option value="MALE">Male</option>
                  <option value="FEMALE">Female</option>
                </select>
              </div>
              <div className={styles.formGroup}>
                <label htmlFor="age">Age</label>
                <input
                  type="number"
                  id="age"
                  name="age"
                  step="1"
                  min="1"
                  max={2147483647}
                  value={+currentUser.age || ""}
                  onChange={(e) =>
                    setCurrentUser({ ...currentUser, age: e.target.value })
                  }
                />
              </div>
              <button className={styles.submitButton}>Save Changes</button>
            </form>
          </div>
        </div>
      ) : (
        <div id="preloader"></div>
      )}
    </>
  );
};

export default EditUser;
