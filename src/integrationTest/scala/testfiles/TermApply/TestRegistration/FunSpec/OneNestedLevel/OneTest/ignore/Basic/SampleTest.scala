package testfiles.TermApply.TestRegistration.FunSpec.OneNestedLevel.OneTest.ignore.Basic

import org.scalatest.funspec.AnyFunSpec

class SampleTest extends AnyFunSpec {

  describe("air conditioner") {
    ignore("should lower temperature when cooling selected") {
      val x = 3
    }
  }
}