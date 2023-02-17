package dummy

import org.scalatest._

class SampleTest {

  "Air conditioner" should {
    "lower temperature when cooling selected" in {
      checkCooling()
    }
  }
}