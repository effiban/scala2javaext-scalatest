package testfiles.TermApplyInfix.TestRegistration.WordSpec.OneNestedLevel.should.OneTest.WithTags

import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec

class SampleTest extends AnyWordSpec {

  "Air conditioner" should {
    "lower temperature when cooling selected" taggedAs(Tag("tag1"), Tag("tag2")) in {
      val x = 3
    }
  }
}