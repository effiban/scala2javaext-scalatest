package dummy

import org.scalatest._

class SampleTest {

  Feature("air conditioner") {
    Scenario("cooling", Tag("tag1"), Tag("tag2")) {
      checkCooling()
    }
  }
}