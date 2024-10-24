package testfiles.TermApply.TestRegistration.FunSuite.test.One.WithTags.One

import org.scalatest.Tag
import org.scalatest.funsuite.AnyFunSuite

class SampleTest extends AnyFunSuite {

  test("check", Tag("myTag")) {
    val x = 3
  }
}