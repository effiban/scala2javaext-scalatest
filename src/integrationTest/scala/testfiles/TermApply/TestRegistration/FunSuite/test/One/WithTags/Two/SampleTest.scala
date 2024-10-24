package testfiles.TermApply.TestRegistration.FunSuite.test.One.WithTags.Two

import org.scalatest.Tag
import org.scalatest.funsuite.AnyFunSuite

class SampleTest extends AnyFunSuite {

  test("check", Tag("tag1"), Tag("tag2")) {
    val x = 3
  }
}