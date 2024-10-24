package testfiles.TermApplyInfix.TestRegistration.WordSpec.TwoNestedLevels.shouldWhich

import org.scalatest.wordspec.AnyWordSpec

class SampleTest extends AnyWordSpec {

  "Coffee machine" should {
    "provide a button" which {
      "prepares an Espresso" in {
        val x = 3
      }
    }
  }
}