package dummy

import org.scalatest._

class SampleTest {

  "Air conditioner" can {
    "lower temperature when cooling selected" in {
      checkCooling()
    }
  }
}