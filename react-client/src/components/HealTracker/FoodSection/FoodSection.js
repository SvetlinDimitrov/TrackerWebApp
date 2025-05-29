import React, { useState, useEffect, useContext } from "react";
import { Outlet, useNavigate, useParams } from "react-router-dom";

import styles from "./FoodSection.module.css";
import * as PathCreator from "../../../util/PathCreator";
import api from "../../../util/api";
import { NotificationContext } from "../../../context/Notification";
import { AuthContext } from "../../../context/UserCredentials";

const FoodSection = () => {
  const { recordId, storageId } = useParams();
  const { setFailedMessage } = useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;
  const [selectedStorage, setSelectedStorage] = useState(undefined);
  const navigate = useNavigate();

  useEffect(() => {
    setSelectedStorage(undefined);
    const fetchData = async () => {
      try {
        const response = await api.get(
          `/storage?recordId=${recordId}&storageId=${storageId}`,
          {
            headers: { Authorization: `Bearer ${userToken}` },
          }
        );
        setSelectedStorage(response.data);
      } catch (error) {
        setFailedMessage({
          message:
            "Something went wrong with record creation. Please try again later!",
          flag: true,
        });
        navigate(PathCreator.storagePath(recordId, storageId));
      }
    };
    fetchData();
  }, [recordId, storageId, userToken, setFailedMessage, navigate]);

  if (selectedStorage === undefined) {
    return <div id="preloader"></div>;
  }

  return (
    <>
      <div className={styles.container}>
        <h2>Food Section </h2>
        <div className={styles.container_totalCount}>
          <span className={styles.container_totalCount_title}>
            Listed Foods For {selectedStorage.name}
          </span>
          <span className={styles.container_totalCount_title}>
            Total Count: {selectedStorage.consumedCalories}
          </span>
        </div>
        <div className={styles.container_foodDetails}>
          {selectedStorage.foods.map((food, index) => (
            <div
              className={styles.container_foodDetails_food}
              key={food.name + index}
              onClick={() =>
                navigate(
                  PathCreator.storagePath(recordId, storageId) +
                    `/${food.id}?isCustom=${food.foodClass === 'Custom' ? true : false}`
                )
              }
            >
              <div className={styles.container_foodDetails_food_info}>
                <div>{food.description}</div>
                <div>{food.size} {food.measurement}</div>
              </div>
              <div className={styles.container_foodDetails_food_info}>
                <div>{food.calories.name}: {food.calories.amount} {food.calories.unit}</div>
              </div>
            </div>
          ))}
        </div>
        <div className={styles.container_button_container}>
          <button
            className={styles.container_addButton}
            onClick={() =>
              navigate(
                PathCreator.storagePath(recordId, storageId) + "/foodMenu"
              )
            }
          >
            Add Food
          </button>
          <button
            className={styles.container_addButton}
            onClick={() =>
              navigate(
                PathCreator.storagePath(recordId, storageId) + "/customFood"
              )
            }
          >
            More Options
          </button>
        </div>
      </div>
      <Outlet />
    </>
  );
};

export default FoodSection;
