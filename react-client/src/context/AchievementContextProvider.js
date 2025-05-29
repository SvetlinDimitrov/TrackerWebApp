import { createContext, useState } from "react";

export const AchievementContext = createContext();

const AchievementContextProvider = ({ children }) => {
  const [achievementToPass, setAchievementToPass] = useState();

  const data = { achievementToPass , setAchievementToPass };

  return (
    <AchievementContext.Provider value={data}>{children}</AchievementContext.Provider>
  );
};
export default AchievementContextProvider;