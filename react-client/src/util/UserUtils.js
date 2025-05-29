export const validatedUserToken = (user) => {
  
  if (user === undefined) {
    return "userNotCompleted";
  }

  const { userDetails } = user.userData;

  if (userDetails !== "COMPLETED") {
    return "userNotCompleted";
  }

  const { timeForExpiration } = user.tokenInfo;

  if (new Date() > new Date(timeForExpiration)) {
    return "dateExpired";
  }

  return "valid";
};
