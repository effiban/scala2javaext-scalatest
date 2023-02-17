package dummy

import org.scalatest._

class SampleTest {

  describe("air conditioners") {
    they("should lower temperature when cooling selected") {
      checkCooling()
    }
  }
}