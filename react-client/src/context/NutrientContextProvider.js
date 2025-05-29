import { createContext, useState } from "react";

export const NutrientContext = createContext();


const NutrientContextProvider = ({ children }) => {
    const [nutrient, setNutrient] = useState();
  
    const data = { nutrient , setNutrient };
  
    return (
      <NutrientContext.Provider value={data}>{children}</NutrientContext.Provider>
    );
  };
  export default NutrientContextProvider;