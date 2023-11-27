import { Routes, Route } from "react-router-dom";

import UserAuthProvider from "./context/UserAuth";
import UserAuthGuard from "./context/UserAuthGuard";

import NutrientInfo from "./components/HealTracker/Nutrients/NutrientInfo";
import Logout from "./components/User/Logout/Logout";
import Header from "./components/Header/Header";
import Home from "./components/HomePage/Home";
import Footer from "./components/Footer/Footer";
import Login from "./components/User/RegisterLogin/Login";
import Register from "./components/User/RegisterLogin/Register";
import Error from "./components/Error/Error";
import NutrientTemplate from "./components/HealTracker/Nutrients/NutrientTemplate";
import EditUser from "./components/User/EditUser/EditUser";
import NotificationProvider from "./context/Notification";
import FoodContextProvider from "./context/FoodContext";
import HealthTracker from "./components/HealTracker/HealthTracker";
import SelectedRecordHolder from "./components/HealTracker/Record/SelectedRecordHolder";
import SelectedStorageHolder from "./components/HealTracker/Storage/SelectedStorageHolder";
function App() {
  return (
    <>
      <UserAuthProvider>
        <NotificationProvider>
          <FoodContextProvider>
            <Header />
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/nutrientInfo" element={<NutrientInfo />}>
                <Route
                  path=":nutrition/:nutritionType"
                  element={<NutrientTemplate />}
                />
              </Route>
              <Route path="*" element={<Error />} />
              <Route path="/editUser" element={<EditUser />} />
              <Route path="/logout" element={<Logout />} />
              <Route element={<UserAuthGuard />}>
                <Route path="/health-tracker" element={<HealthTracker />}>
                  <Route
                    path="record/:recordId"
                    element={<SelectedRecordHolder />}
                  >
                    <Route
                      path="storage/:storageId"
                      element={<SelectedStorageHolder />}
                    />
                  </Route>
                </Route>
              </Route>
            </Routes>
            <Footer />
          </FoodContextProvider>
        </NotificationProvider>
      </UserAuthProvider>
    </>
  );
}

export default App;
