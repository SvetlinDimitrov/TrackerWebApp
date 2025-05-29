const HeroSection = () => {
  return (
    <section id="hero" className="hero">
      <div className="container position-relative">
        <div className="row gy-5" data-aos="fade-in">
          <div className="col-lg-6 order-2 order-lg-1 d-flex flex-column justify-content-center text-center text-lg-start">
            <h2>
              Welcome to <span> TrackerApp</span>
            </h2>
            <p>
              Our app is designed to help you maintain a healthy lifestyle by
              tracking your nutrition and exercise habits. With TrackerApp, you
              can easily monitor your daily intake of various nutrients, keep
              track of your workouts, and set personal health goals.
            </p>
          </div>
          <div className="col-lg-6 order-1 order-lg-2">
            <img
              style={{ width: "500px", height: "400px" }}
              src="https://graphicsfamily.com/wp-content/uploads/edd/2020/05/Free-Global-Health-Care-PSD-Logo-Template-PNG-Transparent-1536x1536.png"
              className="img-fluid"
              alt=""
              data-aos="zoom-out"
              data-aos-delay="100"
            />
          </div>
        </div>
      </div>
    </section>
  );
};

export default HeroSection;
