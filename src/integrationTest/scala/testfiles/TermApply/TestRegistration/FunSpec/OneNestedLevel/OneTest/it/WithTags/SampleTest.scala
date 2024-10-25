package testfiles.TermApply.TestRegistration.FunSpec.OneNestedLevel.OneTest.it.WithTags

import org.scalatest.Tag
import org.scalatest.funspec.AnyFunSpec

class SampleTest extends AnyFunSpec {

  describe("air conditioner") {
    it("should lower temperature when cooling selected", Tag("tag1"), Tag("tag2")) {
      val x = 3
    }
  }
}