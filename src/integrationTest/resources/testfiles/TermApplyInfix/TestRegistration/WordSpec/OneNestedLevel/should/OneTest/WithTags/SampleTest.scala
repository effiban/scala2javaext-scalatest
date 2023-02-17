package dummy

import org.scalatest._

class SampleTest {

  "Air conditioner" should {
    "lower temperature when cooling selected" taggedAs(Tag("tag1"), Tag("tag2")) in {
      checkCooling()
    }
  }
}