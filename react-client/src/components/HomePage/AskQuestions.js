const AskQuestions = () => {
  return (
    <section id="faq" className="faq">
      <div className="container" data-aos="fade-up">
        <div className="row gy-4">
          <div className="col-lg-4">
            <div className="content px-xl-5">
              <h3>
                Frequently Asked <strong>Questions</strong>
              </h3>
              <p>
                Here are some of the most common questions we get from our
                users. If you don't find the answer you're looking for, feel
                free to contact us.
              </p>
            </div>
          </div>

          <div className="col-lg-8">
            <div
              className="accordion accordion-flush"
              id="faqlist"
              data-aos="fade-up"
              data-aos-delay="100"
            >
              <div className="accordion-item">
                <h3 className="accordion-header">
                  <button
                    className="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#faq-content-1"
                  >
                    <span className="num">1.</span>
                    How do I start tracking my health data with TrackerApp?
                  </button>
                </h3>
                <div
                  id="faq-content-1"
                  className="accordion-collapse collapse"
                  data-bs-parent="#faqlist"
                >
                  <div className="accordion-body">
                    To start tracking your health data, simply create an account
                    and fill out your profile. Then, you can start logging your
                    meals, workouts, and other health data.
                  </div>
                </div>
              </div>
              {/* <!-- # Faq item--> */}

              <div className="accordion-item">
                <h3 className="accordion-header">
                  <button
                    className="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#faq-content-2"
                  >
                    <span className="num">2.</span>
                    What kind of data can I track with TrackerApp?
                  </button>
                </h3>
                <div
                  id="faq-content-2"
                  className="accordion-collapse collapse"
                  data-bs-parent="#faqlist"
                >
                  <div className="accordion-body">
                    With TrackerApp, you can track a wide range of health data,
                    including your meals, workouts, sleep, weight, and more. You
                    can also set personal health goals and monitor your progress
                    over time.
                  </div>
                </div>
              </div>
              {/* <!-- # Faq item--> */}

              <div className="accordion-item">
                <h3 className="accordion-header">
                  <button
                    className="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#faq-content-3"
                  >
                    <span className="num">3.</span>
                    How does TrackerApp keep my data secure?
                  </button>
                </h3>
                <div
                  id="faq-content-3"
                  className="accordion-collapse collapse"
                  data-bs-parent="#faqlist"
                >
                  <div className="accordion-body">
                    We take data security very seriously at TrackerApp. We use
                    industry-standard encryption methods to ensure that your
                    data is safe and secure. We also never share your data with
                    third parties without your explicit consent.
                  </div>
                </div>
              </div>
              {/* <!-- # Faq item--> */}
              <div className="accordion-item">
                <h3 className="accordion-header">
                  <button
                    className="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#faq-content-4"
                  >
                    <span className="num">4.</span>
                    Can I share my progress with friends or family?
                  </button>
                </h3>
                <div
                  id="faq-content-4"
                  className="accordion-collapse collapse"
                  data-bs-parent="#faqlist"
                >
                  <div className="accordion-body">
                    Yes, TrackerApp allows you to share your progress with
                    friends and family. You can choose what information to share
                    and who to share it with. This can be a great way to stay
                    motivated and accountable.
                  </div>
                </div>
              </div>
              {/* <!-- # Faq item--> */}

              <div className="accordion-item">
                <h3 className="accordion-header">
                  <button
                    className="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#faq-content-5"
                  >
                    <span className="num">5.</span>
                    What devices is TrackerApp compatible with?
                  </button>
                </h3>
                <div
                  id="faq-content-5"
                  className="accordion-collapse collapse"
                  data-bs-parent="#faqlist"
                >
                  <div className="accordion-body">
                    TrackerApp is compatible with most modern smartphones,
                    tablets, and computers. We offer a mobile app for both iOS
                    and Android devices, and our website is optimized for both
                    desktop and mobile browsers.
                  </div>
                </div>
              </div>
              {/* <!-- # Faq item--> */}
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default AskQuestions;
