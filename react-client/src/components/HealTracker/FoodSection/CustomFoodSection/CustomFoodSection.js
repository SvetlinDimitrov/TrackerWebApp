import React, { useState, useEffect, useContext } from "react";
import { Outlet, useNavigate, useParams, useLocation } from "react-router-dom";

import styles from "./CustomFoodSection.module.css";
import * as PathCreator from "../../../../util/PathCreator";
import { NotificationContext } from "../../../../context/Notification";
import { AuthContext } from "../../../../context/UserCredentials";
import api from "../../../../util/api";

const CustomFoodSection = () => {
  const location = useLocation();
  const [customFoods, setCustomFoods] = useState();
  const { recordId, storageId } = useParams();
  const { setFailedMessage, setSuccessfulMessage } =
    useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await api.get(`/food/all`, {
          headers: { Authorization: `Bearer ${userToken}` },
        });
        setCustomFoods(response.data);
      } catch (error) {
        setFailedMessage({
          message:
            "Something went wrong with fetching data. Please try again later!",
          flag: true,
        });
        navigate(PathCreator.storagePath(recordId, storageId));
        return;
      }
    };
    fetchData();
  }, [recordId, navigate, setFailedMessage, userToken, storageId, location]);

  if (customFoods === undefined) {
    return <div id="preloader"></div>;
  }
  const onClose = () => {
    navigate(PathCreator.storagePath(recordId, storageId));
  };
  const handleDelete = async (food) => {
    if (
      !window.confirm(
        "Are you sure you want to delete " + food.description + " ?"
      )
    ) {
      return;
    }
    try {
      await api.delete(`/food/${food.id}`, {
        headers: { Authorization: `Bearer ${userToken}` },
      });
      setSuccessfulMessage({
        message: "Successfully deleted " + food.description + " !",
        flag: true,
      });
      navigate(PathCreator.customFoodPath(recordId, storageId));
    } catch (error) {
      setFailedMessage({
        message:
          "Something went wrong with deleting data. Please try again later!",
        flag: true,
      });
      navigate(PathCreator.storagePath(recordId, storageId));
      return;
    }
  };

  return (
    <>
      <div className={styles.overlay}>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        <div className={styles.popupSection}>
          <h1>Custom Food Section</h1>
          <div className={styles.customFoodList}>
            <h2>Custom Food Items</h2>
            <div className={styles.foodItems}>
              {customFoods.map((item, index) => (
                <div
                  key={index}
                  className={styles.foodItem}
                  onClick={() =>
                    navigate(
                      PathCreator.storagePath(recordId, storageId) +
                        "/foodMenu/" +
                        item.foodClass +
                        "/" +
                        item.id +
                        "?isCustom=true"
                    )
                  }
                >
                  <span>{item.description}</span>
                  <div className={styles.buttonSection}>
                    <button
                      onClick={(event) => {
                        event.stopPropagation();
                        handleDelete(item);
                      }}
                    >
                      Delete
                    </button>
                  </div>
                </div>
              ))}
            </div>
            <button
              className={styles.customFoodList_addButton}
              onClick={() =>
                navigate(
                  PathCreator.storagePath(recordId, storageId) +
                    "/customFood" +
                    "/create"
                )
              }
            >
              Create Food
            </button>
          </div>
        </div>
      </div>
      <Outlet />
    </>
  );
};

export default CustomFoodSection;
