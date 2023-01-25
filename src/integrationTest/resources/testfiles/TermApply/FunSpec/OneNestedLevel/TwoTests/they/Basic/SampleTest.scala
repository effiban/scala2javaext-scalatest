package dummy

import org.scalatest._

class SampleTest {

  describe("air conditioners") {
    they("should lower temperature when cooling selected") {
      checkCooling()
    }
    they("should raise temperature when heating selected") {
      checkHeating()
    }
  }
}