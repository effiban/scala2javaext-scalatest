package testfiles.TermApply.TestRegistration.FeatureSpec.OneScenario.WithFixtureArgs.One

import org.scalatest.featurespec.AnyFeatureSpec

class SampleTest extends AnyFeatureSpec {

  Feature("air conditioner") {
    Scenario("cooling") { (x: Int) =>
      val y = x + 1
    }
  }
}