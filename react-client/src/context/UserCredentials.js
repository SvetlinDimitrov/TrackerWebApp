import { createContext, useState } from "react";

export const AuthContext = createContext();

const UserCredentials = ({ children }) => {
  const [user, setUser] = useState(() => {
    const user = localStorage.getItem("user");

    if (user !== null && user !== undefined) {
      return JSON.parse(user);
    }

    return undefined;
  });
  const loginUser = (user) => {
    setUser(user);
    localStorage.setItem("user", JSON.stringify(user));
  };
  const logout = () => {
    setUser(undefined);
    localStorage.removeItem("user");
  };

  const authUser = { user, loginUser, logout };

  return (
    <AuthContext.Provider value={authUser}>{children}</AuthContext.Provider>
  );
};
export default UserCredentials;
