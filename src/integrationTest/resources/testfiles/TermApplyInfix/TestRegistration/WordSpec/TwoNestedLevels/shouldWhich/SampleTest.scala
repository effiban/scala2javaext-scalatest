package dummy

import org.scalatest._

class SampleTest {

  "Coffee machine" should {
    "provide a button" which {
      "prepares an Espresso" in {
        verifyEspressoButton()
      }
    }
  }
}