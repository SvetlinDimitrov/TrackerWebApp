import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import styles from "../NutrientFeatureTemplate.module.css";

const descriptionInfo = {
  immuneFunction:
    "To boost your immune system, focus on a diet rich in vitamin C (citrus, peppers), vitamin D (fish, dairy), zinc (meat, nuts), vitamin A (sweet potatoes, spinach), vitamin E (nuts, spinach), protein (meat, beans), iron (red meat, lentils), selenium (Brazil nuts, seafood), folate (leafy greens, citrus), vitamin B6 (poultry, bananas), and omega-3 fatty acids (fatty fish, flaxseeds). Additionally, stay hydrated, get enough sleep, manage stress, and exercise regularly for overall health. Consult a healthcare professional for personalized advice.",
  growthAndDevelopment:
    "Optimal growth and development require a diet rich in protein (meat, beans), calcium (dairy, leafy greens), iron (red meat, lentils), zinc (meat, nuts), vitamin D (fish, fortified dairy), vitamin A (sweet potatoes, spinach), vitamin C (citrus, peppers), vitamin E (nuts, broccoli), folate (leafy greens, citrus), vitamin B12 (animal products), and omega-3 fatty acids (fatty fish, flaxseeds). Include a variety of nutrient-dense foods for a well-rounded approach. Consider consulting a healthcare professional for personalized guidance.",
  cognitiveFunctionAndBrainHealth:
    "To optimize cognitive function and brain health, prioritize nutrients like omega-3 fatty acids (fatty fish, walnuts), antioxidants such as vitamin E (nuts, spinach), and vitamin C (citrus fruits, berries). Additionally, focus on B-vitamins, including B6 (poultry, bananas) and B12 (fish, meat), which support nerve function. Include sources of choline (eggs, broccoli) for neurotransmitter production and ensure an adequate intake of iron (red meat, beans) for oxygen transport to the brain. Lastly, maintain hydration and consider consulting a healthcare professional for personalized advice on a brain-boosting diet.",
  boneHealth:
    "For bone health, prioritize calcium-rich foods like dairy and leafy greens, along with vitamin D sources such as fatty fish and fortified dairy to enhance calcium absorption. Include foods high in phosphorus like meat and dairy for bone structure. Vitamin K from leafy greens aids in bone mineralization. Magnesium, found in nuts and whole grains, supports bone density. Protein (meat, beans) is essential for bone maintenance. Ensure a well-balanced diet, and consider consulting a healthcare professional for personalized guidance on maintaining strong and healthy bones.",
  physicalPerformanceAndFitness:
    "To enhance physical performance and fitness, focus on protein-rich foods like meat and beans for muscle repair. Carbohydrates from whole grains and fruits provide energy for workouts. Stay hydrated with water and electrolytes. Include iron-rich foods like red meat to support oxygen transport during exercise. Omega-3 fatty acids from fatty fish and nuts reduce inflammation. Vitamins like B6 (poultry, bananas) and B12 (fish, meat) aid in energy metabolism. Potassium-rich foods like bananas and oranges help prevent muscle cramps. Maintain a balanced diet, consider individual needs, and consult fitness or healthcare professionals for personalized advice.",
  agingAndLongevity:
    "For aging and longevity, prioritize nutrients that support overall health. Antioxidants like vitamin C (citrus fruits, berries) and vitamin E (nuts, spinach) combat oxidative stress. Omega-3 fatty acids from fatty fish and flaxseeds promote heart health and reduce inflammation. Vitamin D (fatty fish, fortified dairy) aids in bone health and immune function. Ensure an adequate intake of protein (meat, beans) for muscle maintenance. Include foods rich in fiber (whole grains, fruits) for digestive health. Stay hydrated and consider incorporating anti-inflammatory spices like turmeric. Consult with healthcare professionals for personalized advice on nutrition and lifestyle choices for aging well.",
};

const NutrientTemplateDescriptionFeature = () => {
  const { featureType } = useParams();
  const [description, setDescription] = useState();

  useEffect(() => {
    setDescription(descriptionInfo[featureType]);
  }, [featureType]);

  return (
    <div className={styles.body_container_section}>
      <p className={styles.body_container_description}> {description}</p>
    </div>
  );
};
export default NutrientTemplateDescriptionFeature;
