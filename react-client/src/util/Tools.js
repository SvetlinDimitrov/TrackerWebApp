import { faInfoCircle } from "@fortawesome/free-solid-svg-icons";
import chroma from "chroma-js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  arc,
  axisBottom,
  axisLeft,
  easeLinear,
  pie,
  scaleBand,
  scaleLinear,
  select,
  selectAll,
  interpolate,
  format,
} from "d3";
import { useLayoutEffect, useRef, useState, useEffect, useMemo } from "react";
import { Link } from "react-router-dom";
import { calcAverageValue } from "./RecordUtils";
import NoDataFoundImage from "./No-data-found-banner.webp";

export function Gauge({ width, height, type, data, className }) {
  const diameter = width / 2 + height / 2;
  const centerX = width / 2;
  const centerY = height / 2;
  const textRef = useRef();

  let averageValue = null;

  if (type === "Vitamin") {
    averageValue = calcAverageValue(data, type);
  } else if (type === "Mineral") {
    averageValue = calcAverageValue(data, type);
  } else if (type === "Macronutrient") {
    averageValue = calcAverageValue(data, type);
  } else {
    averageValue =
      (data.dailyConsumedCalories / data.dailyCaloriesToConsume) * 100;
  }

  const pieData = pie()([averageValue, 100 - averageValue]);
  const foregroundArc = pieData[0];

  useLayoutEffect(() => {
    const percentage = averageValue / 100;
    const endAngle =
      percentage * (foregroundArc.endAngle - foregroundArc.startAngle);

    const arcGenerator = arc()
      .innerRadius(diameter / 3.2)
      .outerRadius(diameter / 2.2)
      .startAngle(0);

    const path = select(`.${className}`)
      .attr("d", arcGenerator.endAngle(-foregroundArc.endAngle)())
      .attr("fill", "#78909C");

    path
      .transition()
      .duration(4000) // duration of the transition in milliseconds
      .attrTween("d", function () {
        const i = interpolate(0, endAngle);
        return function (t) {
          return arcGenerator.endAngle(i(t))();
        };
      });
    const text = select(textRef.current);

    text
      .transition()
      .duration(4000) // duration of the transition in milliseconds
      .tween("text", function () {
        const i = interpolate(0, averageValue);
        return function (t) {
          this.textContent = `${i(t).toFixed(2)}%`;
        };
      });
  }, [
    diameter,
    averageValue,
    foregroundArc.endAngle,
    foregroundArc.startAngle,
    className,
  ]);

  if (averageValue === null) {
    return <div id="preloader"></div>;
  }

  return (
    <div>
      <svg width={width} height={height}>
        <g transform={`translate(${centerX},${centerY})`}>
          <path
            d={arc()
              .innerRadius(diameter / 3.2)
              .outerRadius(diameter / 2.2)
              .startAngle(-0)
              .endAngle(-100)()}
            fill="gray"
          />

          <path className={className} fill="gray" />

          <text
            className="text"
            transform={`translate(${0},${-10})`}
            textAnchor="middle"
            fill="#CFD8DC" // light blue gray
            fontSize={width / 12 + "px"} // increase font size
            fontWeight="bold" // make font bold
          >
            {type}
          </text>
          <text
            ref={textRef}
            className="text"
            fill="#CFD8DC" // light blue gray
            transform={`translate(${0},${+25})`}
            textAnchor="middle"
            fontSize={width / 12 + "px"}
          >
            {averageValue.toFixed(2) + "%"}
          </text>
        </g>
      </svg>
    </div>
  );
}

export function PipeChar({ width, height, data }) {
  const xScale = scaleLinear().domain([0, 100]).range([0, width]);
  const [currentColor, setCurrentColor] = useState("#67C240");

  useLayoutEffect(() => {
    const fills = Math.ceil(data.consumed / data.max);
    const durationPerFill = 4000 / fills;

    const colors = generateColors("#67C240", fills + 1);

    let transition = selectAll(".rect")
      .attr("fill", colors[1])
      .attr("stroke", "#455A64");

    for (let i = 1; i < fills; i++) {
      console.log(i);
      transition = transition
        .transition()
        .ease(easeLinear)
        .duration(durationPerFill)
        .attr("width", xScale(100))
        .on("end", () => setCurrentColor(colors[i])) // update color on transition end
        .transition()
        .duration(0)
        .attr("width", 0)
        .attr("fill", colors[i + 1]);
    }

    transition
      .transition()
      .ease(easeLinear)
      .duration(durationPerFill)
      .attr("width", xScale(data.precented % 100));
  }, []);

  return (
    <div
      className="WorkHere"
      width={width + 80}
      height={height + 60}
      style={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#546e7a", // lighter gray background
        borderRadius: 15,
        overflow: "hidden",
        padding: 20,
        color: "#ffffff", // white text
        fontFamily: "Arial", // Arial font
        fontSize: 16, // adjust font size
      }}
    >
      <div style={{ display: "flex" }}>
        <span
          style={{
            padding: 5,
            color: "#ffffff",
            fontFamily: "Arial",
            fontSize: 18,
            fontWeight: "bold",
          }}
        >
          <AnimatedNumber value={data.consumed.toFixed(2)} />
        </span>
        <div
          style={{
            width: width,
            height: height,
            backgroundColor: currentColor,
            borderRadius: 15,
            overflow: "hidden",
          }}
        >
          <svg style={{ width: "100%", height: "100%" }}>
            <rect
              className="rect"
              width={0}
              style={{
                height: "100%",
              }}
            />
          </svg>
        </div>
        <span
          style={{
            padding: 5,
            color: "#ffffff",
            fontFamily: "Arial",
            fontSize: 18,
            fontWeight: "bold",
          }}
        >
          {data.max.toFixed(2)}
        </span>
      </div>
      
      {data.consumed === 0 && (
        <div
          style={{
            color: "#67C240",
            fontSize: 16,
            marginTop: 10,
            lineHeight: "1.5",
            textAlign: "justify",
          }}
        >
          Nothing is consumed !{" "}
          {data.type === "Vitamin" && (
            <Link to={"/nutrientInfo/vitamin/" + data.name}>
              {" "}
              <FontAwesomeIcon
                icon={faInfoCircle}
                style={{ fontSize: "24px", marginRight: "5px" }}
              />
            </Link>
          )}
          {data.type === "Mineral" && (
            <Link to={"/nutrientInfo/mineral/" + data.name}>
              {" "}
              <FontAwesomeIcon
                icon={faInfoCircle}
                style={{ fontSize: "24px", marginRight: "5px" }}
              />
            </Link>
          )}
          
        </div>
      )}
      {data.type === "Vitamin" && data.consumed && data.consumed !== 0 && (
        <div
          style={{
            color: "#67C240",
            fontSize: 16,
            marginTop: 10,
            lineHeight: "1.5",
            textAlign: "justify",
          }}
        >
          Consumed <AnimatedNumber value={data.consumed && data.consumed.toFixed(2)} />{" "}
          {" "} {data.measurement} of Vitamin{" "}
          {data.name}.
          <Link to={"/nutrientInfo/vitamin/" + data.name}>
            {" "}
            <FontAwesomeIcon
              icon={faInfoCircle}
              style={{ fontSize: "24px", marginRight: "5px" }}
            />
          </Link>
        </div>
      )}
      {data.type === "Mineral" && data.consumed !== 0 && (
        <div
          style={{
            color: "#67C240",
            fontSize: 16,
            marginTop: 10,
            lineHeight: "1.5",
            textAlign: "justify",
          }}
        >
          Consumed <AnimatedNumber value={data.consumed.toFixed(2)} />
          {" "} {data.measurement} of{" "}
          {data.name}.
          <Link to={"/nutrientInfo/mineral/" + data.name}>
            {" "}
            <FontAwesomeIcon
              icon={faInfoCircle}
              style={{ fontSize: "24px", marginRight: "5px" }}
            />
          </Link>
        </div>
      )}
      {data.type === "Calories" && data.consumed !== 0 && (
        <div
          style={{
            color: "#67C240",
            fontSize: 16,
            marginTop: 10,
            lineHeight: "1.5",
            textAlign: "justify",
          }}
        >
          Consumed <AnimatedNumber value={data.consumed.toFixed(2)} />{" "}
          {data.measurement} in the {data.name}.
        </div>
      )}
      {(data.type === "Macronutrient" ||
        data.type === "Fat" ||
        data.type === "Carbohydrates") && (
        <div
          style={{
            color: "#67C240",
            fontSize: 16,
            marginTop: 10,
            lineHeight: "1.5",
            textAlign: "justify",
          }}
        >
          Consumed <AnimatedNumber value={data.consumed.toFixed(2)} />
          {" "} {data.measurement} of{" "}
          {data.name.replace(/([A-Z])/g, " $1").trim()}.
          {data.type === "Macronutrient" ? (
            <Link to={`/nutrientInfo/macronutrient/${data.name === "Carbohydrate" ? "Carbohydrates" : data.name}`}>
              {" "}
              <FontAwesomeIcon
                icon={faInfoCircle}
                style={{ fontSize: "24px", marginRight: "5px" }}
              />
            </Link>
          ) : (
            <Link
              to={
                "/nutrientInfo/macronutrientTypes/" +
                data.name
              }
            >
              {" "}
              <FontAwesomeIcon
                icon={faInfoCircle}
                style={{ fontSize: "24px", marginRight: "5px" }}
              />
            </Link>
          )}
        </div>
      )}
    </div>
  );
}

export function Gauge2({
  width,
  height,
  diameter,
  legendRectSize,
  legendSpacing,
  fat,
  carbohydrates,
  protein,
}) {
  const svgRef = useRef(null);
  const colors = useMemo(() => ["#6a5071", "#5E74C2", "#C25E74"], []);
  const legendInfo = ["Fat", "Carbohydrates", "Protein"];
  let sections = [fat * 9, carbohydrates * 4, protein * 4];
  const sum = fat * 9 + carbohydrates * 4 + protein * 4;
  if (sections.every((value) => value === 0)) {
    sections = [33.33, 33.33, 33.33];
  }

  useEffect(() => {
    const svg = select(svgRef.current);
    const arcGenerator = arc()
      .innerRadius(0)
      .outerRadius(diameter / 2);

    const pieGenerator = pie()
      .value((d) => d)
      .sort(null);

    const arcs = pieGenerator(sections);

    const path = svg
      .selectAll("path")
      .data(arcs)
      .join("path")
      .attr("fill", (d, i) => colors[i])
      .attr("stroke", "white")
      .attr("stroke-width", 2);

    let previousTransition = null;

    path.each(function (d, i) {
      const totalDuration = 6000; // Total duration of the animation
      const duration = totalDuration * (sections[i] / sum); // Adjust the duration based on the section's percentage of the total

      const dCopy = { ...d }; // Create a copy of d

      const currentTransition = select(this)
        .transition()
        .duration(duration)
        .attrTween("d", function () {
          const i = interpolate(dCopy.startAngle, dCopy.endAngle);
          return function (t) {
            dCopy.endAngle = i(t);
            return arcGenerator(dCopy);
          };
        });
      // Set the stroke-wid

      if (previousTransition) {
        previousTransition.each(() => {
          select(this).transition().delay(duration);
        });
      }

      previousTransition = currentTransition;
    });
  }, [sections, diameter, sum, colors]);

  return (
    <svg ref={svgRef} width={width} height={height}>
      <g transform={`translate(${diameter / 1.9}, ${diameter / 1.9})`}>
        {pie()(sections).map((section, index) => {
          return (
            <g key={index}>
              <path
                d={arc()
                  .innerRadius(0)
                  .outerRadius(diameter / 2)
                  .startAngle(section.startAngle)
                  .endAngle(section.endAngle)()}
              />
              <title>{((section.data / sum) * 100).toFixed(2)}%</title>
            </g>
          );
        })}
      </g>
      <g
        transform={`translate(${diameter + 31}, ${
          diameter / 2 -
          (sections.length * (legendRectSize - 10 + legendSpacing)) / 2
        })`}
      >
        {sections.map((section, index) => {
          return (
            <g
              key={index}
              transform={`translate(0, ${
                index * (legendRectSize + legendSpacing)
              })`}
            >
              <rect
                width={legendRectSize}
                height={legendRectSize}
                fill={colors[index]}
              />
              <text
                x={legendRectSize + legendSpacing}
                y={legendRectSize}
                fill={colors[index]}
              >
                {legendInfo[index]}
              </text>
            </g>
          );
        })}
      </g>
    </svg>
  );
}
export function BarChart2({ height, info, dataLength }) {
  
  dataLength = dataLength < 0 ? 0 : dataLength;
  let widthIncreaser = info.length > dataLength ? dataLength : info.length;

  info = info
  .filter((v, i, a) => a.findIndex(t => (t.dataNames === v.dataNames)) === i)
  .slice(0, dataLength);
  
  const dataNames = info.map((item, index) => {
    let truncatedName = item.dataNames.split(" ").slice(0, 2).join(" "); 
    return truncatedName + " " +index;
  });  const data = info.map((item) => item.data);
  const typeData = info.map((item) => item.typeData);

  
  const margin = { top: 10, right: 10, bottom: 40, left: 40 };

  const width = 100 * widthIncreaser;

  const chartWidth = width - margin.left - margin.right;
  const chartHeight = height - margin.top - margin.bottom;

  const x = scaleBand().range([0, chartWidth]).padding(0.2).domain(dataNames);
  
  const y = scaleLinear()
    .range([chartHeight, 0])
    .domain([0, Math.max(...data)]);

  const maxValue = Math.max(...data);
  let yAxis = axisLeft(y);

  if (maxValue >= 1000) {
    const formatFunction = format(".2s");
    yAxis = yAxis.tickFormat(formatFunction);
  }

  const rectsRef = useRef(null);

  useEffect(() => {
    const svg = select(rectsRef.current);
    svg
      .selectAll("rect")
      .data(data)
      .join("rect")
      .attr("x", (d, i) => x(dataNames[i]))
      .attr("width", x.bandwidth())
      .attr("fill", "steelblue")
      .transition()
      .duration(4000)
      .attr("y", (d) => y(d))
      .attr("height", (d) => chartHeight - y(d));
  }, [data]);

  return (
    <svg width={width} height={height}>
      <g transform={`translate(${margin.left}, ${margin.top})`}>
        <g
          ref={(node) => select(node).call(yAxis)}
          style={{ fontSize: "12px", fontFamily: "Verdana" }}
        />
        <g
          transform={`translate(0, ${chartHeight})`}
          ref={(node) => {
            const axis = axisBottom(x);
            select(node)
              .call(axis)
              .selectAll("text")
              .attr(
                "transform",
                (d, i) => `translate(0, ${i % 2 === 0 ? 0 : 15})`
              );
          }}
          style={{ fontSize: "13px", fontFamily: "Verdana" }}
        />
        <g ref={rectsRef}>
          {data.map((d, i) => (
            <g key={i}>
              <title>{typeData[i]}</title>
              <rect
                className="rect"
                x={x(dataNames[i])}
                y={chartHeight}
                width={x.bandwidth()}
                height={0}
                fill="steelblue"
              />
            </g>
          ))}
        </g>
      </g>
    </svg>
  );
}
export function GroupedBarChart({ height, info, dataLength }) {
  
  info = info.slice(0, dataLength);
  
  const dataNames = info.map((item) => item.dataNames);
  const data = info.map((item) => item.data);
  const typeData = info.map((item) => item.typeData);

  const margin = { top: 10, right: 10, bottom: 40, left: 40 };

  const width = data.every((d) => d[0] === 0) ? 95 * 8 : 95 * dataLength;
  const chartWidth = width - margin.left - margin.right;
  const chartHeight = height - margin.top - margin.bottom;

  const x = scaleBand().range([0, chartWidth]).padding(0.2).domain(dataNames);
  const subX = scaleBand()
    .domain([0, 1, 2, 3])
    .range([0, x.bandwidth()])
    .padding(0.05);

  const y = scaleLinear()
    .range([chartHeight, 0])
    .domain([0, Math.max(...data.map((item) => item[2]))]);
  const colors = ["steelblue", "green", "red", "purple"];

  useEffect(() => {
    selectAll(".bar")
      .data(data.flat())
      .transition()
      .duration(4000)
      .attr("y", (d) => y(d))
      .attr("height", (d) => chartHeight - y(d));
  }, [data, y, chartHeight]);

  if (data.every((d) => d[0] === 0)) {
    return (
      <svg width={width} height={height}>
        {data.every((d) => d[0] === 0) && (
          <image
            href={NoDataFoundImage}
            x={(width - width / 1.8) / 2}
            y={(height - height / 1.8) / 2}
            width={width / 1.8}
            height={height / 1.8}
          />
        )}
      </svg>
    );
  }

  return (
    <svg width={width} height={height}>
      <g transform={`translate(${margin.left}, ${margin.top})`}>
        <g
          ref={(node) => select(node).call(axisLeft(y))}
          style={{ fontSize: "18px", fontFamily: "Verdana" }}
        />
        <g
          transform={`translate(0, ${chartHeight})`}
          ref={(node) => {
            const axis = axisBottom(x);
            select(node)
              .call(axis)
              .selectAll("text")
              .attr(
                "transform",
                (d, i) => `translate(0, ${i % 2 === 0 ? 0 : 15})`
              );
          }}
          style={{ fontSize: "16px", fontFamily: "Verdana" }}
        />
        {dataNames.map((dName, i) => (
          <g key={i} transform={`translate(${x(dName)}, 0)`}>
            {data[i].map((d, j) => (
              <g key={j}>
                <rect
                  className="bar"
                  x={subX(j)}
                  y={chartHeight}
                  width={subX.bandwidth()}
                  height={0}
                  fill={colors[j % colors.length]}
                >
                  <title>{`${typeData[i][j]}`}</title>
                </rect>
              </g>
            ))}
          </g>
        ))}
      </g>
    </svg>
  );
}

export function AnimatedNumber({ value }) {
  const [displayValue, setDisplayValue] = useState(0);

  useEffect(() => {
    const targetValue = parseFloat(value);
    const interval = setInterval(() => {
      setDisplayValue((prevValue) => {
        const newValue = prevValue + targetValue / 100;
        if (newValue < targetValue) {
          return newValue;
        } else {
          clearInterval(interval);
          return targetValue;
        }
      });
    }, 40);

    return () => clearInterval(interval);
  }, [value]);

  return <span>{displayValue.toFixed(2)}</span>;
}

function generateColors(startColor, count) {
  const colors = [startColor];
  for (let i = 1; i < count; i++) {
    const color = chroma(colors[i - 1])
      .darken(0.4)
      .hex();
    colors.push(color);
  }
  return colors;
}
