package testfiles.TermApply.TestRegistration.FunSpec.OneNestedLevel.TwoTests.they.Basic

import org.scalatest.funspec.AnyFunSpec

class SampleTest extends AnyFunSpec {

  describe("air conditioners") {
    they("should lower temperature when cooling selected") {
      val x = 3
    }
    they("should raise temperature when heating selected") {
      val y = 4
    }
  }
}