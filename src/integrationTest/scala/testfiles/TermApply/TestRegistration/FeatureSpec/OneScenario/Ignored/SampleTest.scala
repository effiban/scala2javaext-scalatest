package testfiles.TermApply.TestRegistration.FeatureSpec.OneScenario.Ignored

import org.scalatest.featurespec.AnyFeatureSpec

class SampleTest extends AnyFeatureSpec {

  Feature("air conditioner") {
    ignore("cooling") {
      val x = 3
    }
  }
}