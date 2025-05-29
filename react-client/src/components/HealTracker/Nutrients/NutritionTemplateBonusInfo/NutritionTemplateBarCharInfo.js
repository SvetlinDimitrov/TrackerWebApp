import { useEffect, useState, useContext } from "react";
import { useNavigate, useParams, useLocation } from "react-router-dom";

import api from "../../../../util/api";
import { NotificationContext } from "../../../../context/Notification";
import { AuthContext } from "../../../../context/UserCredentials";
import * as PathCreator from "../../../../util/PathCreator";
import { BarChart2 } from "../../../../util/Tools";
import styles from "../NutritionTemplate.module.css";

const NutritionTemplateBarCharInfo = () => {
  const navigate = useNavigate();
  const { setFailedMessage } = useContext(NotificationContext);
  const { user } = useContext(AuthContext);
  const userToken = user.tokenInfo.token;
  const query = new URLSearchParams(useLocation().search);
  const { nutrition, nutritionType } = useParams();
  const sort = query.get("sort");
  const limit = query.get("limit");
  const min = query.get("min");
  const max = query.get("max");
  const type = query.get("type");
  const [data, setData] = useState();

  const [modifyData, setModifyData] = useState({
    sort,
    limit,
    min,
    max,
    selectedOptionsOfData: type,
  });

  useEffect(() => {
    setData(undefined);
    const nutrientName = nutritionType === "Carbohydrates" ? "Carbohydrate" : nutritionType;
    const fetchData = async () => {
      try{
        const response = await api.get(
          `/food/embedded/${
            type
          }/filter?nutrientName=${encodeURIComponent(nutrientName)}&limit=${limit}&desc=${
            sort === "DO" ? true : false
          }&min=${min}&max=${max}`,
          {
            headers: { Authorization: `Bearer ${userToken}` },
          }
        );
        setData(
          response.data.map((food) => {
            return {
              data: food.amount,
              dataNames: food.description,
              typeData: food.amount,
            };
          })
        );
      }catch(error){
        setFailedMessage({
          flag: true,
          message: error.response.data.message,
        });
        navigate(`/nutrientInfo/${nutrition}/${nutritionType}`);
      }
    };

    fetchData();
  }, [nutrition, nutritionType, sort, limit, min, max, type, userToken, navigate , setFailedMessage]);

  const handleOnClick = () => {
    navigate(
      PathCreator.nutrientInfo(
        nutrition,
        nutritionType,
        "barCharStatistic" +
          "?limit=" +
          modifyData.limit +
          "&sort=" +
          modifyData.sort +
          "&min=" +
          modifyData.min +
          "&max=" +
          modifyData.max +
          "&type=" +
          modifyData.selectedOptionsOfData
      )
    );
  };

  if (data === undefined) {
    return <div id="preloader"></div>;
  }
  return (
    <div className={styles.body_container_barChar}>
      <p className={styles.body_container_barChar_p}>
        The information provided on this platform is sourced from the FoodData
        Central (FDC) database, which is maintained by the National Agricultural
        Library of the United States Department of Agriculture (USDA). The data
        can be accessed and downloaded directly from the official FDC website at
        <a
          href="https://fdc.nal.usda.gov/download-datasets.html"
          target="_blank"
          rel="noopener noreferrer"
          style={{ margin: "10px" }}
        >
          Link Here
        </a>
        .
      </p>
      <div className={styles.diagram_container}>
        <div className={styles.containerHolder2}>
          <h3 className={styles.reportsH3}>Modify Data</h3>
          <button
            style={{
              marginTop: "20px",
              padding: "10px 20px",
              backgroundColor: "#007bff",
              color: "#fff",
              border: "none",
              borderRadius: "5px",
              cursor: "pointer",
              fontSize: "16px",
            }}
            onClick={handleOnClick}
          >
            Submit
          </button>
        </div>
        <div>
          <label>
            Limit:
            <input
              type="number"
              className={styles.reportOptions}
              value={modifyData.limit}
              onChange={(e) =>
                setModifyData({ ...modifyData, limit: e.target.value })
              }
            />
          </label>
          <label>
            Sorting:
            <select
              className={styles.reportOptions}
              selected={modifyData.sort}
              value={modifyData.sort}
              onChange={(e) =>
                setModifyData({ ...modifyData, sort: e.target.value })
              }
            >
              <option value="AO">Ascending</option>
              <option value="DO">Descending</option>
            </select>
          </label>
        </div>
        <div>
          <h3>Food Type</h3>
          <label>
            <input
              type="checkbox"
              value="finalFoods"
              checked={modifyData.selectedOptionsOfData === "finalFoods"}
              onChange={() =>
                setModifyData({
                  ...modifyData,
                  selectedOptionsOfData: "finalFoods",
                })
              }
            />
            FoundationFoods
          </label>
          <label>
            <input
              type="checkbox"
              value="surveyFoods"
              checked={modifyData.selectedOptionsOfData === "surveyFoods"}
              onChange={() =>
                setModifyData({
                  ...modifyData,
                  selectedOptionsOfData: "surveyFoods",
                })
              }
            />
            SurveyFoods
          </label>
          <label>
            <input
              type="checkbox"
              value="brandedFoods"
              checked={modifyData.selectedOptionsOfData === "brandedFoods"}
              onChange={() =>
                setModifyData({
                  ...modifyData,
                  selectedOptionsOfData: "brandedFoods",
                })
              }
            />
            BrandedFoods
          </label>
        </div>
        <div
          style={{ display: "flex", flexDirection: "column", width: "200px" }}
        >
          <label style={{ marginBottom: "10px" }}>
            Min Value:
            <input
              type="number"
              value={modifyData.min}
              onChange={(e) =>
                setModifyData({ ...modifyData, min: e.target.value })
              }
              style={{ width: "100%", padding: "5px" }}
            />
          </label>
          <label>
            Max Value:
            <input
              type="number"
              value={modifyData.max}
              onChange={(e) =>
                setModifyData({ ...modifyData, max: e.target.value })
              }
              style={{ width: "100%", padding: "5px" }}
            />
          </label>
        </div>
      </div>
      <div className={styles.container_BarChar2}>
        <BarChart2 height={550} info={data} dataLength={limit} />
      </div>
    </div>
  );
};
export default NutritionTemplateBarCharInfo;
