import { useContext } from "react";
import { useNavigate } from "react-router-dom";

import { AuthContext } from "../../../context/UserCredentials";
import api from "../../../util/api";
import { NotificationContext } from "../../../context/Notification";
import styles from "./Settings.module.css";

const Settings = () => {
  const { logout, user } = useContext(AuthContext);
  const navigate = useNavigate();
  const { setSuccessfulMessage, setFailedMessage } =
    useContext(NotificationContext);

  const logoutUser = () => {
    logout();
    navigate("/");
  };
  const deleteAccount = async () => {
    if (
      window.confirm("Are you sure you want to delete your account?") === false
    )
      return;
    try {
      await api.delete("/auth", {
        headers: { Authorization: `Bearer ${user.tokenInfo.token}` },
      });
      await setSuccessfulMessage({
        message: "Account was deleted successfully!",
        flag: true,
      });
      logout();
    } catch (error) {
      await setFailedMessage({
        message: "Something went wrong try again later!",
        flag: true,
      });
    }
    navigate("/");
  };

  return (
    <div className={styles.body}>
      <div className={styles.settingsContainer}>
        <div className={styles.settingsTitle}>User Settings</div>
        <div className={styles.optionsContainer}>
          <div
            className={styles.settingsOption}
            onClick={() => navigate("/settings/editUser")}
          >
            Edit User
          </div>
          <div className={styles.settingsOption} onClick={() => logoutUser()}>
            Logout
          </div>
          <div
            className={styles.settingsOption}
            onClick={() => deleteAccount()}
          >
            Delete Account
          </div>
        </div>
      </div>
    </div>
  );
};

export default Settings;
