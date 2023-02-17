package dummy

import org.scalatest._

class SampleTest {

  describe("air conditioner") {
    it("should lower temperature when cooling selected", Tag("tag1"), Tag("tag2")) {
      checkCooling()
    }
  }
}