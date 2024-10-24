package testfiles.TermApplyInfix.TestRegistration.FlatSpec.itShouldExpectationIn.WithFixtureArgs.One

import org.scalatest.flatspec.AnyFlatSpec

class SampleTest extends AnyFlatSpec {

  it should "save" in { (x: Int) =>
    val y = x + 1
  }
}