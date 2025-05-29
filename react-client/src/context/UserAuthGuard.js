import { useContext, useEffect, useState } from "react";
import { Outlet, useLocation } from "react-router-dom";
import { AuthContext } from "./UserCredentials";

import { NotificationContext } from "./Notification";
import { validatedUserToken } from "../util/UserUtils";
import { useNavigate } from "react-router-dom";

const UserAuthGuard = () => {
  const { user, logout } = useContext(AuthContext);
  const validatedUser = validatedUserToken(user);
  const navigate = useNavigate();
  const location = useLocation();
  const { setFailedMessage } = useContext(NotificationContext);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const halfAuthPaths = (path) => {
      let halfPermissionPaths = ["/settings", "/achievements"];
      if (
        halfPermissionPaths.some((path) => location.pathname.startsWith(path))
      ) {
        return true;
      } else if (path === "/nutrientInfo") {
        return true;
      } else if (path.startsWith("/nutrientInfo")) {
        halfPermissionPaths = [
          "/nutrientInfo/mineral",
          "/nutrientInfo/macronutrient",
          "/nutrientInfo/vitamin",
        ];
        return halfPermissionPaths.some((path) =>
          location.pathname.startsWith(path)
        );
      }
      return false;
    };

    if (user === undefined) {
      setFailedMessage({
        message: "Please login to access this page!",
        flag: true,
      });
      navigate("/");
    } else if (validatedUser === "dateExpired") {
      setFailedMessage({
        message: "Your token has expired please login again!",
        flag: true,
      });
      logout();
      navigate("/");
    } else if (
      validatedUser === "userNotCompleted" &&
      !halfAuthPaths(location.pathname)
    ) {
      setFailedMessage({
        message:
          "Please complete your profile to access this page! Go to Settings to complete your profile!",
        flag: true,
      });
      navigate("/");
    }
    setIsAuthenticated(true);
  }, [
    user,
    validatedUser,
    navigate,
    location.pathname,
    logout,
    setFailedMessage,
    location,
  ]);

  return isAuthenticated ? <Outlet /> : null;
};

export default UserAuthGuard;
