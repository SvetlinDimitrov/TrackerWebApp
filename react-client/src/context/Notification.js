import React, { createContext, useState } from "react";

export const NotificationContext = createContext();

const NotificationProvider = ({ children }) => {
  const [successfulMessage, setSuccessfulMessage] = useState({message:'something' , flag:false});
  const [failedMessage, setFailedMessage] = useState({message:'something' , flag:false});

  const messageInfo = {
    successfulMessage,
    failedMessage,
    setSuccessfulMessage,
    setFailedMessage,
  };
  return (
    <NotificationContext.Provider value={messageInfo}>
      {children}
    </NotificationContext.Provider>
  );
};

export default NotificationProvider;