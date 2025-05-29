import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";

import api from "../../../util/api";
import { NotificationContext } from "../../../context/Notification";
import styles from "./LoginRegister.module.css";
import AlertMessage from "../../Notifications/AlertMessage";

const Register = () => {
  const navigate = useNavigate();
  const { setFailedMessage, setSuccessfulMessage } =
    useContext(NotificationContext);
  const [user, setUser] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const submitHandler = async (e) => {
    e.preventDefault();
    try {
      if (user.password !== user.confirmPassword) {
        setFailedMessage({
          message: "Passwords do not match!",
          flag: true,
        });
        return;
      }
      await api.post("/auth/register", user);
      setSuccessfulMessage({
        message: "Registered successfully!",
        flag: true,
      });
      navigate("/login");
    } catch (error) {
      if (error.response.status === 400) {
        setFailedMessage({
          message: error.response.data.errorMessages[0],
          flag: true,
        });
        setUser({ ...user, password: "", confirmPassword: "" });
        navigate("/register");
        return;
      }
      setFailedMessage({
        message: "Something went wrong! Please try again later!",
        flag: true,
      });
      navigate("/");
    }
  };

  return (
    <div className={styles.body}>
      <AlertMessage />
      <div className={styles.logRegContainer}>
        <h2 className={styles.h2}>Register</h2>
        <form
          className={styles.form}
          method="POST"
          onSubmit={(e) => submitHandler(e)}
        >
          <input
            className={styles.input}
            type="text"
            name="username"
            minLength={4}
            required
            placeholder="Username"
            value={user.username}
            onChange={(e) => setUser({ ...user, username: e.target.value })}
          />

          <input
            className={styles.input}
            type="email"
            name="email"
            required
            email="true"
            placeholder="Email"
            value={user.email}
            onChange={(e) => setUser({ ...user, email: e.target.value })}
          />

          <input
            className={styles.input}
            type="password"
            name="password"
            minLength={5}
            required
            placeholder="Password"
            value={user.password}
            onChange={(e) => setUser({ ...user, password: e.target.value })}
          />

          <input
            className={styles.input}
            type="password"
            name="confirmPassword"
            required
            placeholder="Confirm Password"
            value={user.confirmPassword}
            onChange={(e) =>
              setUser({ ...user, confirmPassword: e.target.value })
            }
          />

          <button className={styles.button} type="submit">
            Register
          </button>
        </form>
      </div>
    </div>
  );
};

export default Register;
