import { Link } from "react-router-dom";
import { AuthContext } from "../../context/UserCredentials";
import { useContext } from "react";

const Header = () => {
  const { user } = useContext(AuthContext);

  return (
    <>
      <header id="header" className="header d-flex align-items-center">
        <div className="container-fluid container-xl d-flex align-items-center justify-content-between">
          <Link className="logo d-flex align-items-center"></Link>
          <h1>
            TrackerApp<span>.</span>
          </h1>

          <nav id="navbar" className="navbar">
            <ul>
              <li>
                <Link to={"/"}>Home</Link>
              </li>
              {user === undefined || user.tokenInfo === undefined ? (
                <>
                  <li>
                    <Link to={"/register"}>Register</Link>
                  </li>
                  <li>
                    <Link to={"/login"}>Login</Link>
                  </li>
                </>
              ) : (
                <>
                  <li>
                    <Link to={"/settings"}>Settings</Link>
                  </li>
                </>
              )}

              <li className="dropdown">
                <Link>
                  <span>Services</span>{" "}
                  <i className="bi bi-chevron-down dropdown-indicator"></i>
                </Link>
                <ul>
                  <li>
                    <Link to={"/nutrientInfo"}>Nutrient Information</Link>
                  </li>

                  <li>
                    <Link to={"/health-tracker"}>Health Tracker</Link>
                  </li>
                  <li>
                    <Link to={"/achievements"}>Achievement Tracker</Link>
                  </li>
                </ul>
              </li>
            </ul>
          </nav>
          <i className="mobile-nav-toggle mobile-nav-show bi bi-list"></i>
          <i className="mobile-nav-toggle mobile-nav-hide d-none bi bi-x"></i>
        </div>
      </header>
    </>
  );
};

export default Header;
