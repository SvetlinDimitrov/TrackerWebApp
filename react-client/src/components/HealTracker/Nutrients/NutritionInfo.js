import { useRef } from "react";
import { Link, Outlet } from "react-router-dom";
import styles from "./NutritionInfo.module.css";
import AlertMessage from "../../Notifications/AlertMessage";

const allNutritious = {
  allMacronutrients: ["Carbohydrates", "Protein", "Fat"],
  allMacronutrientsTypes: [
    "Sugar",
    "Fiber",
    "Saturated Fat",
    "Polyunsaturated Fat",
    "Monounsaturated Fat",
    "Trans Fat",
  ],
  allVitamins: [
    "Vitamin A",
    "Vitamin D (D2 + D3)",
    "Vitamin E",
    "Vitamin K",
    "Vitamin C",
    "Vitamin B1 (Thiamin)",
    "Vitamin B2 (Riboflavin)",
    "Vitamin B3 (Niacin)",
    "Vitamin B5 (Pantothenic acid)",
    "Vitamin B6",
    "Vitamin B7 (Biotin)",
    "Vitamin B9 (Folate)",
    "Vitamin B12",
  ],
  allMinerals: [
    "Calcium , Ca",
    "Phosphorus , P",
    "Magnesium , Mg",
    "Sodium , Na",
    "Potassium , K",
    "Iron , Fe",
    "Zinc , Zn",
    "Copper , Cu",
    "Manganese , Mn",
    "Iodine , I",
    "Selenium , Se",
    "Molybdenum , Mo",
  ],
};

const NutritionInfo = () => {
  const outletRef = useRef(null);
  const onClickHandler = () => {
    outletRef.current.scrollIntoView({ behavior: "smooth" });
  };
  return (
    <>
    <AlertMessage />
      <section id="services" className="services sections-bg">
        <div key={"MainContainer"} className="container" data-aos="fade-up">
          <div key={"title"} className="section-header">
            <h2>Nutrition Information</h2>
            <p>
              The saying, "You are what you eat," succinctly captures the
              essence of nutrition. Coined by the French gastronome Anthelme
              Brillat-Savarin, this phrase encapsulates the profound connection
              between our dietary choices and our overall well-being. On this
              page, I want to show you the best foods that increase a variety of
              nutrients related to the specific topic. Please take notice that
              all of these foods come from my database, so there may be some
              that I have missed, which could be better or greater than those
              shown. The nutrition information is provided by ChatGPT, so keep
              that in mind as well.
            </p>
          </div>

          <div
            key={"sections"}
            className="row gy-4"
            data-aos="fade-up"
            data-aos-delay="100"
          >
            <div key={"sectionVitamin"} className="col-lg-4 col-md-6">
              <div className="service-item position-relative">
                <h3>Vitamins</h3>
                <p>
                  Vitamins are essential for overall health, playing diverse
                  roles in immune function, bone strength, and metabolism.
                  Vitamin A supports vision and skin health, while the B-complex
                  vitamins aid in metabolism and nervous system function.
                  Vitamin C boosts the immune system, and Vitamin D is crucial
                  for bone health. Vitamin E acts as an antioxidant, and Vitamin
                  K plays a key role in blood clotting and bone maintenance.
                  Including a variety of nutrient-rich foods ensures a
                  well-rounded approach to health.
                </p>
                <div className={styles.container}>
                  <li className={styles.dropdown}>
                    <Link>Vitamin Types</Link>
                    <div className={styles.dropdownContent}>
                      {allNutritious.allVitamins.map((vitamin) => (
                        <Link
                          key={vitamin}
                          onClick={() => onClickHandler()}
                          to={`vitamin/${vitamin}`}
                        >
                          {vitamin}
                        </Link>
                      ))}
                    </div>
                  </li>
                </div>
              </div>
            </div>

            <div key={"sectionMineral"} className="col-lg-4 col-md-6">
              <div className="service-item position-relative">
                <h3>Minerals</h3>
                <p>
                  Minerals are essential micronutrients crucial for various
                  bodily functions. Zinc supports the immune system in meat and
                  legumes. Selenium, an antioxidant in nuts and whole grains,
                  aids overall health. Iron, found in red meat and leafy greens,
                  is vital for oxygen transport. Folate, crucial for DNA
                  synthesis, is present in leafy vegetables and legumes. Copper,
                  essential for the immune system, is in nuts and seafood. A
                  balanced diet ensures a proper intake of these minerals for
                  overall health.
                </p>
                <div className={styles.container}>
                  <li className={styles.dropdown}>
                    <Link>Macronutrient Types</Link>
                    <div className={styles.dropdownContent}>
                      {allNutritious.allMinerals.map((mineral) => (
                        <Link
                          key={mineral}
                          onClick={() => onClickHandler()}
                          to={`mineral/${mineral}`}
                        >
                          {mineral}
                        </Link>
                      ))}
                    </div>
                  </li>
                </div>
              </div>
            </div>

            <div key={"sectionMacronutrients"} className="col-lg-4 col-md-6">
              <div className="service-item position-relative">
                <h3>Macronutrients</h3>
                <p>
                  Macronutrients are the three main components of our diet that
                  provide energy and support various bodily functions.
                  Carbohydrates are our primary energy source, found in foods
                  like grains, fruits, and vegetables. Proteins are essential
                  for muscle repair and overall body function, found in meat,
                  dairy, and plant-based sources. Fats play a role in energy
                  storage, insulation, and nutrient absorption, sourced from
                  oils, nuts, and avocados. Balancing these macronutrients in
                  our diet is crucial for maintaining a healthy and
                  well-functioning body.
                </p>
                <div className={styles.container}>
                  <li className={styles.dropdown}>
                    <Link>Macronutrient</Link>
                    <div className={styles.dropdownContent}>
                      {allNutritious.allMacronutrients.map((macronutrient) => (
                        <Link
                          key={macronutrient}
                          onClick={() => onClickHandler()}
                          to={`macronutrient/${macronutrient}`}
                        >
                          {macronutrient}
                        </Link>
                      ))}
                    </div>
                  </li>
                  <li className={styles.dropdown}>
                    <Link>Different Types</Link>
                    <div className={styles.dropdownContent}>
                      {allNutritious.allMacronutrientsTypes.map(
                        (macronutrientType) => (
                          <Link
                            key={macronutrientType}
                            onClick={() => onClickHandler()}
                            to={`macronutrientTypes/${macronutrientType}`}
                          >
                            {macronutrientType}
                          </Link>
                        )
                      )}
                    </div>
                  </li>
                </div>
              </div>
            </div>

            <div key={"sectionImmuneFunction"} className="col-lg-4 col-md-6">
              <div className="service-item  position-relative">
                <h3>Immune Function</h3>
                <p>
                  A well-balanced diet with adequate vitamins and minerals is
                  crucial for a robust immune system. Nutrients such as vitamin
                  C, vitamin D, zinc, and antioxidants contribute to immune
                  health.
                </p>

                <Link
                  to={"feature/immuneFunction"}
                  className="readmore stretched-link"
                  onClick={() => onClickHandler()}
                >
                  Best Foods
                  <i className="bi bi-arrow-right"></i>
                </Link>
              </div>
            </div>
            {/* <!-- End Service Item --> */}

            <div key={"Growth and Development"} className="col-lg-4 col-md-6">
              <div className="service-item position-relative">
                <h3>Growth and Development (Childhood and Adolescence)</h3>
                <p>
                  During childhood and adolescence, proper nutrition is
                  essential for physical growth, cognitive development, and the
                  formation of healthy bones and tissues. Nutrients such as
                  protein, calcium, vitamin D, and iron are particularly
                  important during this stage.
                </p>
                <Link
                  to={"feature/growthAndDevelopment"}
                  className="readmore stretched-link"
                  onClick={() => onClickHandler()}
                >
                  Best Foods <i className="bi bi-arrow-right"></i>
                </Link>
              </div>
            </div>
            {/* <!-- End Service Item --> */}

            <div
              key={"Cognitive Function and Brain Health"}
              className="col-lg-4 col-md-6"
            >
              <div className="service-item position-relative">
                <h3>Cognitive Function and Brain Health</h3>
                <p>
                  Throughout life, nutrients play a vital role in supporting
                  cognitive function and maintaining brain health. Omega-3 fatty
                  acids, B vitamins, antioxidants, and minerals like zinc and
                  iron are important for brain function.
                </p>
                <Link
                  to={"feature/cognitiveFunctionAndBrainHealth"}
                  className="readmore stretched-link"
                  onClick={() => onClickHandler()}
                >
                  Best Foods <i className="bi bi-arrow-right"></i>
                </Link>
              </div>
            </div>
            {/* <!-- End Service Item --> */}

            <div key={"Bone Health"} className="col-lg-4 col-md-6">
              <div className="service-item position-relative">
                <h3>Bone Health</h3>
                <p>
                  Throughout life, but particularly during childhood,
                  adolescence, and later adulthood, nutrients like calcium,
                  vitamin D, magnesium, and phosphorus are important for
                  maintaining strong and healthy bones.
                </p>
                <Link
                  to={"feature/boneHealth"}
                  className="readmore stretched-link"
                  onClick={() => onClickHandler()}
                >
                  Best Foods <i className="bi bi-arrow-right"></i>
                </Link>
              </div>
            </div>
            {/* <!-- End Service Item --> */}

            <div
              key={"Physical Performance and Fitness"}
              className="col-lg-4 col-md-6"
            >
              <div className="service-item position-relative">
                <h3>Physical Performance and Fitness</h3>
                <p>
                  Athletes and individuals engaged in physical activities
                  require specific nutrients to support energy metabolism,
                  muscle function, and recovery. This includes carbohydrates,
                  proteins, electrolytes, and micronutrients.
                </p>
                <Link
                  to={"feature/physicalPerformanceAndFitness"}
                  className="readmore stretched-link"
                  onClick={() => onClickHandler()}
                >
                  Best Foods <i className="bi bi-arrow-right"></i>
                </Link>
              </div>
            </div>
            {/* <!-- End Service Item --> */}

            <div key={"Aging and Longevity"} className="col-lg-4 col-md-6">
              <div className="service-item position-relative">
                <h3>Aging and Longevity</h3>
                <p>
                  As individuals age, nutrition becomes increasingly important
                  for maintaining health and preventing age-related conditions.
                  Nutrients that support bone health, brain function, and immune
                  function remain crucial.
                </p>
                <Link
                  to={"feature/agingAndLongevity"}
                  className="readmore stretched-link"
                  onClick={() => onClickHandler()}
                >
                  Best Foods <i className="bi bi-arrow-right"></i>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </section>
      <div ref={outletRef}>
        <Outlet />
      </div>
    </>
  );
};

export default NutritionInfo;
