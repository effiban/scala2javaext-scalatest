package dummy

import org.scalatest._

class SampleTest {

  "Air conditioner" must {
    "lower temperature when cooling selected" in {
      checkCooling()
    }
  }
}