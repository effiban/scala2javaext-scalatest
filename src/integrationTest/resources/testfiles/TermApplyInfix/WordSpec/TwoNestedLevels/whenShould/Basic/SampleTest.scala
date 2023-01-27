package dummy

import org.scalatest._

class SampleTest {

  "Air conditioner" when {
    "cooling selected" should {
      "lower temperature" in {
        checkCooling()
      }
    }
  }
}