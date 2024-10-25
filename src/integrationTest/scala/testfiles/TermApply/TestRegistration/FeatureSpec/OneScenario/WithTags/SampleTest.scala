package testfiles.TermApply.TestRegistration.FeatureSpec.OneScenario.WithTags

import org.scalatest.Tag
import org.scalatest.featurespec.AnyFeatureSpec

class SampleTest extends AnyFeatureSpec {

  Feature("air conditioner") {
    Scenario("cooling", Tag("tag1"), Tag("tag2")) {
      val x = 3
    }
  }
}