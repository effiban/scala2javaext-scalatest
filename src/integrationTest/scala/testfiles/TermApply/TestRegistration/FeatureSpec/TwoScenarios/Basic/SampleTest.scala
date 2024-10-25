package testfiles.TermApply.TestRegistration.FeatureSpec.TwoScenarios.Basic

import org.scalatest.featurespec.AnyFeatureSpec

class SampleTest extends AnyFeatureSpec {

  Feature("air conditioner") {
    Scenario("cooling") {
      val x = 3
    }

    Scenario("heating") {
      val y = 4
    }
  }
}