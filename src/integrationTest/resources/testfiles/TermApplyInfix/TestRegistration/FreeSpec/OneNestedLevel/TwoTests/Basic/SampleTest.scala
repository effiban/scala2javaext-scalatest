package dummy

import org.scalatest._

class SampleTest {

  "Air conditioner" - {
    "should lower temperature when cooling selected" in {
      checkCooling()
    }
    "should raise temperature when heating selected" in {
      checkHeating()
    }
  }
}