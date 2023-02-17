package dummy

import org.scalatest._

class SampleTest {

  feature("air conditioner") {
    scenario("cooling") {
      checkCooling()
    }
  }
}