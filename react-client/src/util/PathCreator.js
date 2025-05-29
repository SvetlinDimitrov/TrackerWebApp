export const basicPath = () => {
  return "/health-tracker";
};

export const nutrientInfo = (nutrientType , nutrientName , info) => {
  return `/nutrientInfo/${nutrientType}/${nutrientName}/${info}`;
};
export const basicAchievementPath = () => {
  return "/achievements";
}
export const achievementPathId = (id) => {
  return "/achievements/"+id;
}
export const achievementPathIdReport = (id , type , sortType) => {
  return `/achievements/${id}/reports?type=${type}&sort=${sortType}`;
}

export const recordPath = (recordId) => {
  return "/health-tracker/record/" + recordId;
};

export const storagePath = (recordId, storageId) => {
  return "/health-tracker/record/" + recordId + "/storage/" + storageId;
};

export const basicFoodPath = (
  recordId,
  storageId,
  foodName,
  foodSize,
  fromFoodMenu,
  custom
) => {
  return (
    "/health-tracker/record/" +
    recordId +
    "/storage/" +
    storageId +
    "/foodItem/" +
    foodName +
    "?foodSize=" +
    foodSize +
    "&fromFoodMenu=" +
    fromFoodMenu +
    "&custom=" + 
    custom
  );
};
export const customFoodPath = (
  recordId,
  storageId,
) => {
  return (storagePath(recordId , storageId) + "/customFood");
};

