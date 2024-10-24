package testfiles.TermApplyInfix.TestRegistration.FreeSpec.OneNestedLevel.OneTest.WithTags

import org.scalatest._
import org.scalatest.freespec.AnyFreeSpec

class SampleTest extends AnyFreeSpec {

  "Air conditioner" - {
    "should lower temperature when cooling selected" taggedAs(Tag("tag1"), Tag("tag2")) in {
      val x = 3
    }
  }
}