const AboutUsSection = () => {
  return (
    <section id="about" className="about">
      <div className="container" data-aos="fade-up">
        <div className="section-header">
          <h2>About Us</h2>
        </div>

        <div className="row gy-4">
          <div className="col-lg-6">
            <h3>Empowering Your Health Journey</h3>
            <img
              src="https://static.vecteezy.com/system/resources/previews/000/298/862/original/vector-illustration-of-healthy-lifestyle.jpg"
              className="img-fluid rounded-4 mb-4"
              alt="Tracker App"
            />
            <p>
              At TrackerApp, we believe in the power of data to transform
              health. Our mission is to make it easy for you to understand and
              track your health and fitness data, so you can make informed
              decisions and reach your goals.
            </p>
            <p>
              Whether you're looking to improve your diet, increase your
              physical activity, or just gain a better understanding of your
              health, TrackerApp is here to support you every step of the way.
            </p>
          </div>
          <div className="col-lg-6">
            <div className="content ps-0 ps-lg-5">
              <p className="fst-italic">
                "Your health is your wealth, and TrackerApp is your key to
                preserving it."
              </p>
              <ul>
                <li>
                  <i className="bi bi-check-circle-fill"></i> Easily track your
                  daily nutrition and exercise habits.
                </li>
                <li>
                  <i className="bi bi-check-circle-fill"></i> Gain insights into
                  your health with detailed statistics and visualizations.
                </li>
                <li>
                  <i className="bi bi-check-circle-fill"></i> Set personal
                  health goals and monitor your progress over time.
                </li>
              </ul>
              <p>
                Join the TrackerApp community today and start your journey
                towards a healthier life.
              </p>

              <div className="position-relative mt-4">
                <img
                  src="https://images.pexels.com/photos/572056/pexels-photo-572056.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                  className="img-fluid rounded-4"
                  alt="Tracker App Community"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default AboutUsSection;
