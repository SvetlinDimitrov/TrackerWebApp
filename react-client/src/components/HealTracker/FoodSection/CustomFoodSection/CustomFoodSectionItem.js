import { useEffect, useState, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";

import FoodItemBluePrint from "../FoodItemBluePrint";
import api from "../../../../util/api";
import { AuthContext } from "../../../../context/UserAuth";
import { FoodContext } from "../../../../context/FoodContext";
import { NotificationContext } from "../../../../context/Notification";

import * as PathCreator from "../../../../util/PathCreator";

const CustomFoodSectionItem = () => {
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const { allFoods } = useContext(FoodContext);
  const { setSuccessfulMessage, setFailedMessage } =
    useContext(NotificationContext);
  const userToken = user.tokenInfo.token;
  const { recordId, storageId, foodName } = useParams();
  const [food, setFood] = useState(null);
  useEffect(() => {
    const food = allFoods.find((food) => food.name === foodName);
    setFood(food);
  }, [allFoods, foodName]);

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
            food.name +
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
    navigate(PathCreator.storagePath(recordId, storageId));
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
export default CustomFoodSectionItem;
