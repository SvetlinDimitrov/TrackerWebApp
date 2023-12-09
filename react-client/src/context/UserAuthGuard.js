import { useContext, useEffect } from "react";
import { Outlet, useLocation } from "react-router-dom";
import { AuthContext } from "./UserAuth";

import { NotificationContext } from "./Notification";
import { validatedUserToken } from "../util/UserUtils";
import { useNavigate } from "react-router-dom";

const UserAuthGuard = () => {
  const { user, logout } = useContext(AuthContext);
  const validatedUser = validatedUserToken(user);
  const navigate = useNavigate();
  const location = useLocation();
  const { setFailedMessage } = useContext(NotificationContext);

  useEffect(() => {
    const halfAuthPaths = [
      "/settings",
      "/achievements",
      "/nutrientInfo/mineral",
      "/nutrientInfo/macronutrient",
      "/nutrientInfo/vitamin",
    ];
    if (validatedUser === "dateExpired") {
      setFailedMessage({
        message: "Your token has expired please login again!",
        flag: true,
      });
      logout();
      navigate("/");
      return;
    } else if (user === undefined) {
      setFailedMessage({
        message: "Please login to access this page!",
        flag: true,
      });
      navigate("/");
      return;
    } else if (
      validatedUser === "userNotCompleted" &&
      !halfAuthPaths.some((path) => location.pathname.startsWith(path))
    ) {
      setFailedMessage({
        message: "Please complete your profile to access this page! Go to Settings to complete your profile!",
        flag: true,
      });
      navigate("/");
      return;
    } else {
    }
  }, [
    user,
    validatedUser,
    navigate,
    setFailedMessage,
    logout,
    location.pathname,
  ]);

  return <Outlet />;
};

export default UserAuthGuard;
