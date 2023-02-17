package dummy

import org.scalatest._

class SampleTest {

  describe("air conditioner") {
    describe("when cooling selected") {
      it("should lower temperature") {
        checkCooling()
      }
    }
  }
}