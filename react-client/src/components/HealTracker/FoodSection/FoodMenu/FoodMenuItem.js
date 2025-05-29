import { useEffect, useState, useContext } from "react";
import { useNavigate, useParams, useLocation } from "react-router-dom";

import FoodItemBluePrint from "../FoodItemBluePrint";
import api from "../../../../util/api";
import { AuthContext } from "../../../../context/UserCredentials";
import { NotificationContext } from "../../../../context/Notification";

import * as PathCreator from "../../../../util/PathCreator";

const FoodMenuItem = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const navigate = useNavigate();

  const isCustom = queryParams.get("isCustom") === "true";
  const { user } = useContext(AuthContext);
  const { setSuccessfulMessage, setFailedMessage } =
    useContext(NotificationContext);
  const userToken = user.tokenInfo.token;
  const { recordId, storageId, foodId  , foodType} = useParams();
  const [food, setFood] = useState(null);

  useEffect(() => {
    const getData = async () => {
      if (isCustom) {
        try {
          const customFood = await api.get(`/food/${foodId}`, {
            headers: { Authorization: `Bearer ${userToken}` },
          });
          setFood(customFood.data);
        } catch (error) {
          setFailedMessage({
            message:
              "Something went wrong with food addition. Please try again later!",
            flag: true,
          });
          navigate(PathCreator.storagePath(recordId, storageId));
        }
      } else {
        let foodTypeConverted;
        if(foodType === "FinalFood"){
          foodTypeConverted = "foundationFoods"
        }else if (foodType === "Survey"){
          foodTypeConverted = "surveyFoods"
        }else if(foodType === "Branded"){
          foodTypeConverted = "brandedFoods"
        }
        try {
          const customFood = await api.get(`/food/embedded/${foodTypeConverted}/${foodId}`, {
            headers: { Authorization: `Bearer ${userToken}` },
          });
          setFood(customFood.data);
        } catch (error) {
          setFailedMessage({
            message:
              "Something went wrong with food addition. Please try again later!",
            flag: true,
          });
          navigate(PathCreator.storagePath(recordId, storageId));
        }
      }
    };
    getData();
  }, [
    isCustom,
    navigate,
    recordId,
    setFailedMessage,
    storageId,
    userToken,
    foodId,
    foodType,
  ]);

  const handleAddFood = async (food) => {
    if (
      window.confirm(
        "Are you sure you want to add" +
          food.size +
          " " +
          food.measurement +
          " of " +
          food.name +
          "?"
      )
    ) {
      try {
        await api.patch(
          `/storage/${storageId}/addFood?recordId=${recordId}`,
          food,
          {
            headers: { Authorization: `Bearer ${userToken}` },
          }
        );
        setSuccessfulMessage({
          message:
            food.size +
            " " +
            food.measurement +
            " of " +
            food.description +
            " added successfully!",
          flag: true,
        });
      } catch (error) {
        setFailedMessage({
          message:
            "Something went wrong with food addition. Please try again later!",
          flag: true,
        });
      }
      navigate(PathCreator.storagePath(recordId, storageId));
    }
  };

  const onClose = () => {
    if (isCustom) {
      navigate(PathCreator.storagePath(recordId, storageId) + "/customFood");
      return;
    }
    navigate(PathCreator.storagePath(recordId, storageId) + "/foodMenu");
  };

  if (!food) {
    return <div id="preloader"></div>;
  }
  return (
    <FoodItemBluePrint
      foodInfo={food}
      handleAddFood={handleAddFood}
      onClose={onClose}
    />
  );
};

export default FoodMenuItem;
