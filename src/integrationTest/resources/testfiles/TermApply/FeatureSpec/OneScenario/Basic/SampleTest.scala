package dummy

import org.scalatest._

class SampleTest {

  Feature("air conditioner") {
    Scenario("cooling") {
      checkCooling()
    }
  }
}