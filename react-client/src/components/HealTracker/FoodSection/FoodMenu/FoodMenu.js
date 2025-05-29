import { useState , useContext } from "react";
import { useNavigate, useParams, Outlet } from "react-router-dom";

import styles from "./FoodMenu.module.css";
import { AuthContext } from "../../../../context/UserCredentials";
import api from "../../../../util/api";
import * as PathCreator from "../../../../util/PathCreator";

export const FoodMenu = () => {
  const { recordId, storageId } = useParams();
  const { user } = useContext(AuthContext);
  const [allFoods, setAllFoods] = useState([]);
  const navigate = useNavigate();
  const [selectedType, setSelectedType] = useState("surveyFoods");
  const [searchTerm, setSearchTerm] = useState("");
  const userToken = user.tokenInfo.token;

  const onClose = () => {
    navigate(PathCreator.storagePath(recordId, storageId));
  };

  const getFoodsBySearch = async () => {
    try {
      const response = await api.get(
        `/food/embedded/${selectedType}/search?description=${searchTerm}`,
        {
          headers: { Authorization: `Bearer ${userToken}` },
        }
      );
      setAllFoods(response.data);
    } catch (error) {
      console.log(error);
    }
  }

  if (allFoods === undefined) {
    return <div id="preloader"></div>;
  }

  return (
    <>
      <div className={styles.overlay}>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        <div className={styles.container}>
          <div className={styles.container_CloseButton}>
            <h1 className={styles.foodMenuTitle}>List Of Foods</h1>
            <select
              className={styles.selectStyle}
              onChange={(e) => setSelectedType(e.target.value)}
              value={selectedType}
            >
              <option value="finalFoods">Foundation Foods</option>
              <option value="surveyFoods">Survey Foods</option>
              <option value="brandedFoods">Branded Foods</option>
            </select>
            <div className={styles.flexContainer}>
              <input
                type="text"
                value={searchTerm}
                placeholder="Write at least 3 chars..."
                onChange={(e) => setSearchTerm(e.target.value)}
                className={styles.searchInput}
              />
              <button className={styles.searchButton} onClick={getFoodsBySearch} disabled={searchTerm.length < 3}>Search</button>
            </div>
            <div className={styles.container_foodDetails}>
              {allFoods.map((food) => {
                return (
                  <div
                    className={styles.container_foodDetails_food}
                    key={food.id}
                    onClick={() => navigate(PathCreator.storagePath(recordId, storageId)+`/foodMenu/${food.foodClass}/${food.id}?isCustom=${false}`)}
                  >
                    <p className={styles.container_foodDetails_food_info}>
                      {food.description}
                    </p>
                  </div>
                );
              })}
            </div>
          </div>
        </div>
      </div>
      <Outlet />
    </>
  );
};

export default FoodMenu;
