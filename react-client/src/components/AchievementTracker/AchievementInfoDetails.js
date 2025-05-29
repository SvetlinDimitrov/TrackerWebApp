import React, { useEffect, useState, useContext } from "react";
import { useNavigate, useParams, useLocation } from "react-router-dom";

import { AiOutlineInfoCircle } from "react-icons/ai";

import { AchievementContext } from "../../context/AchievementContextProvider";
import Tooltip from "./Tooltip";
import { GroupedBarChart, BarChart2 } from "../../util/Tools";
import styles from "./AchievementInfoDetails.module.css";
import * as PathCreator from "../../util/PathCreator";

const AchievementInfoDetails = () => {
  const navigate = useNavigate();
  const query = new URLSearchParams(useLocation().search);
  const [params, setParams] = useState({ type: query.get("type"), sort: query.get("sort") });
  const { achievementToPass } = useContext(AchievementContext);

  const { achId } = useParams();
  const type = query.get("type");
  const sort = query.get("sort");

  const [selectedSection, setSelectedSection] = useState(0);
  const [sectionData, setSectionData] = useState([]);

  useEffect(() => {
    const fetchData = async (data) => {
      let processedData = processDataByType(data, type);
      processedData = sortData(processedData, sort, type);
      const sections = groupDataIntoSections(processedData, type);
      
      setSectionData(sections);
      setSelectedSection(0);
      setParams({ type, sort }); 
    };

    if (achievementToPass) {
      fetchData(achievementToPass);
    }
  }, [achievementToPass, sort, type]);

  if (!achievementToPass || !sectionData[selectedSection] || params.type !== type || params.sort !== sort) {
    return <div className="preloader"></div>;
  }

  return (
    <div className={styles.diagram}>
      <div className={styles.dailyTitleContainer}>
        <div className={styles.titleContainer}>
          <h1 className={styles.title}>{type} Reports</h1>
          <span className={styles.moreInfo}>
            More Info{" "}
            <Tooltip type={type}>
              <AiOutlineInfoCircle />
            </Tooltip>
          </span>
        </div>
        <div className={styles.draggable_container}>
          {sectionData.map((section, index) => {
            return (
              <div
                key={index}
                onClick={() => setSelectedSection(index)}
                className={styles.section}
              >
                {getScrollableTitle(section, type)}
              </div>
            );
          })}
        </div>
        <div className={styles.diagram_container}>
          <h3 className={styles.reportsH3}>Sort By</h3>
          <div>
            <select
              className={styles.reportOptions}
              defaultValue={sort}
              onChange={(e) =>
                navigate(
                  PathCreator.achievementPathIdReport(
                    achId,
                    type,
                    e.target.value
                  )
                )
              }
            >
              <option value="date_AO">Date AO</option>
              <option value="date_DO">Date DO</option>
              {type !== "Daily" ? <>
              <option value="totalProgress_AO">Total Progress AO</option>
              <option value="totalProgress_DO">Total Progress DO</option>
              <option value="bestProgress_AO">Best Progress AO</option>
              <option value="bestProgress_DO">Best Progress DO</option>
              <option value="worstProgress_AO">Worst Progress AO</option>
              <option value="worstProgress_DO">Worst Progress DO</option>
              <option value="averageProgress_AO"> Average Progress AO</option>
              <option value="averageProgress_DO"> Average Progress DO</option>
              </> : <>
              <option value="progress_AO">Progress AO</option>
              <option value="progress_DO">Progress DO</option>
              </>}
              
            </select>
          </div>
        </div>

        <div className={styles.BarChart2_container}>
          {type === "Daily" ? (
            <BarChart2
              height={400}
              info={sectionData[selectedSection]}
              dataLength={sectionData[selectedSection].length}
            />
          ) : (
            <GroupedBarChart
              height={400}
              info={sectionData[selectedSection]}
              dataLength={sectionData[selectedSection].length}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default AchievementInfoDetails;

function groupDataIntoSections(data, type) {
  let sectionSize;
  if (type === "Weekly") {
    sectionSize = 26;
  } else if (type === "Monthly") {
    sectionSize = 12;
  } else if (type === "Yearly") {
    sectionSize = 5;
  }else if (type === "Daily") {
    sectionSize = 26;
  }

  let sections = [];
  for (let i = 0; i < data.length; i += sectionSize) {
    sections.push(data.slice(i, i + sectionSize));
  }
  return sections;
}
function processDataByType(data, type) {
  if (type === "Weekly") {
    return data.weeklyProgress.map((item) => {
      return {
        data: [
          item.bestProgress,
          item.worstProgress,
          item.totalProgress,
          item.totalProgress / 7,
        ],
        dataNames: item.week,
        typeData: [
          item.bestProgress.toFixed(2) + " " + data.measurement,
          item.worstProgress.toFixed(2) + " " + data.measurement,
          item.totalProgress.toFixed(2) + " " + data.measurement,
          (item.totalProgress / 7).toFixed(2) + " " + data.measurement,
        ],
      };
    });
  } else if (type === "Monthly") {
    return data.monthlyProgress.map((item) => {
      return {
        data: [
          item.bestProgress,
          item.worstProgress,
          item.totalProgress,
          item.totalProgress / getDaysInMonth(item.month),
        ],
        dataNames: item.month,
        typeData: [
          item.bestProgress.toFixed(2) + " " + data.measurement,
          item.worstProgress.toFixed(2) + " " + data.measurement,
          item.totalProgress.toFixed(2) + " " + data.measurement,
          (item.totalProgress / getDaysInMonth(item.month)).toFixed(2) +
            " " +
            data.measurement,
        ],
      };
    });
  } else if (type === "Yearly") {
    return data.yearlyProgress.map((item) => {
      return {
        data: [
          item.bestProgress,
          item.worstProgress,
          item.totalProgress,
          item.totalProgress / 365,
        ],
        dataNames: item.year,
        typeData: [
          item.bestProgress.toFixed(2) + " " + data.measurement,
          item.worstProgress.toFixed(2) + " " + data.measurement,
          item.totalProgress.toFixed(2) + " " + data.measurement,
          (item.totalProgress / 365).toFixed(2) + " " + data.measurement,
        ],
      };
    });
  } else if (type === "Daily") {
    return data.dailyProgress.map((item) => {
      return {
        data: item.progress,
        dataNames: item.date,
        typeData: item.progress + " " + data.measurement,
      };
    });
  }
}
const sortData = (data, sort, type) => {
  if (sort === "date_AO") {
    if (type === "Daily") {
      return data.sort((a, b) => a.dataNames.localeCompare(b.dataNames));
    }
    if (type === "Yearly") {
      return data.sort((a, b) => a.dataNames - b.dataNames);
    }
    return data.sort((a, b) => a.dataNames.localeCompare(b.dataNames));
  } else if (sort === "date_DO") {
    if (type === "Daily") {
      return data.sort((a, b) => b.dataNames.localeCompare(a.dataNames));
    }
    if (type === "Yearly") {
      return data.sort((a, b) => b.dataNames - a.dataNames);
    }
    return data.sort((a, b) => b.dataNames.localeCompare(a.dataNames));
  } else if (sort === "totalProgress_AO") {
    return data.sort((a, b) => a.data[2] - b.data[2]);
  } else if (sort === "totalProgress_DO") {
    return data.sort((a, b) => b.data[2] - a.data[2]);
  } else if (sort === "bestProgress_AO") {
    return data.sort((a, b) => a.data[0] - b.data[0]);
  } else if (sort === "bestProgress_DO") {
    return data.sort((a, b) => b.data[0] - a.data[0]);
  } else if (sort === "worstProgress_AO") {
    return data.sort((a, b) => a.data[1] - b.data[1]);
  } else if (sort === "worstProgress_DO") {
    return data.sort((a, b) => b.data[1] - a.data[1]);
  } else if (sort === "averageProgress_AO") {
    return data.sort((a, b) => a.data[3] - b.data[3]);
  } else if (sort === "averageProgress_DO") {
    return data.sort((a, b) => b.data[3] - a.data[3]);
  } else if (sort === "progress_AO") {
    return data.sort((a, b) => a.data - b.data);
  } else if (sort === "progress_DO") {
    return data.sort((a, b) => b.data - a.data);
  }
};
function getDaysInMonth(data) {
  const [year, month] = data.split("-").map(Number);
  return new Date(year, month, 0).getDate();
}
function getScrollableTitle(section, type) {
  try {
    if (type === "Yearly") {
      return section[0].dataNames;
    }

    const year = section[0].dataNames.split("-")[0];
    const startMonth = section[0].dataNames.split("-")[1];
    const endMonth = section[section.length - 1].dataNames.split("-")[1];
    return `${year}-${startMonth}/${endMonth}`;
  } catch (e) {
    return "";
  }
}
