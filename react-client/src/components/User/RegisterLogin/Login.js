import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";

import { AuthContext } from "../../../context/UserCredentials";
import api from "../../../util/api";
import { NotificationContext } from "../../../context/Notification";

import React from "react";
import styles from "./LoginRegister.module.css";
import AlertMessage from "../../Notifications/AlertMessage";

const Login = () => {
  const navigate = useNavigate();
  const { loginUser } = useContext(AuthContext);
  const { setFailedMessage, setSuccessfulMessage } =
    useContext(NotificationContext);
  const [user, setUser] = useState({
    email: "",
    password: "",
  });

  const submitHandler = async (e) => {
    e.preventDefault();
    try {
      const userTokenResponse = await api.post("/auth/login", user);
      const userToken = userTokenResponse.data.token;
      const newUserTokenInfo = userTokenResponse.data;
      const userDataResponse = await api.get("/auth/details", {
        headers: { Authorization: `Bearer ${userToken}` },
      });

      const userData = {
        userData: userDataResponse.data,
        tokenInfo: newUserTokenInfo,
      };
      loginUser(userData);

      setSuccessfulMessage({
        message: "Logged in successfully!",
        flag: true,
      });
    } catch (error) {
      if (error.response.status === 400) {
        setFailedMessage({
          message: error.response.data.errorMessages[0],
          flag: true,
        });
        setUser({ ...user, password: "" });
        navigate("/login");
        return;
      }
      setFailedMessage({
        message: "Something went wrong!",
        flag: true,
      });
    }
    navigate("/");
  };
  return (
    <div className={styles.body}>
      <AlertMessage />
      <div className={styles.logRegContainer}>
        <h2 className={styles.h2}>Login</h2>
        <form
          className={styles.form}
          method="POST"
          onSubmit={(e) => submitHandler(e)}
        >
          <input
            className={styles.input}
            type="email"
            name="email"
            email="true"
            placeholder="Email"
            value={user.email}
            onChange={(e) => {
              setUser({ ...user, email: e.target.value });
            }}
            required
          />

          <input
            className={styles.input}
            type="password"
            name="password"
            placeholder="Password"
            value={user.password}
            onChange={(e) => {
              setUser({ ...user, password: e.target.value });
            }}
            required
            minLength={5}
          />
          <button className={styles.button}>Login</button>
        </form>
      </div>
    </div>
  );
};

export default Login;
