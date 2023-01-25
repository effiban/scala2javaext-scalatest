package dummy

import org.scalatest._

class SampleTest {

  describe("air conditioner") {
    it("should lower temperature when cooling selected") {
      checkCooling()
    }
    it("should raise temperature when heating selected") {
      checkHeating()
    }
  }
}