const StatsCounter = () => {
    return (
        <section id="stats-counter" className="stats-counter">
      <div className="container" data-aos="fade-up">

        <div className="row gy-4 align-items-center">

          <div className="col-lg-6">
            <img src="assets/img/stats-img.svg" alt="" className="img-fluid"/>
          </div>

          <div className="col-lg-6">

            <div className="stats-item d-flex align-items-center">
              <span data-purecounter-start="0" data-purecounter-end="232" data-purecounter-duration="1" className="purecounter"></span>
              <p><strong>Happy Clients</strong> consequuntur quae diredo para mesta</p>
            </div>
            {/* <!-- End Stats Item --> */}

            <div className="stats-item d-flex align-items-center">
              <span data-purecounter-start="0" data-purecounter-end="521" data-purecounter-duration="1" className="purecounter"></span>
              <p><strong>Projects</strong> adipisci atque cum quia aut</p>
            </div>
            {/* <!-- End Stats Item --> */}

            <div className="stats-item d-flex align-items-center">
              <span data-purecounter-start="0" data-purecounter-end="453" data-purecounter-duration="1" className="purecounter"></span>
              <p><strong>Hours Of Support</strong> aut commodi quaerat</p>
            </div>
            {/* <!-- End Stats Item --> */}

          </div>

        </div>

      </div>
    </section>
    );
}

export default StatsCounter;