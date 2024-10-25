package testfiles.TermApplyInfix.TestRegistration.FlatSpec.subjectShouldExpectationIgnore.Basic

import org.scalatest.flatspec.AnyFlatSpec

class SampleTest extends AnyFlatSpec {

  "process" should "save" ignore {
    val x = 3
  }
}