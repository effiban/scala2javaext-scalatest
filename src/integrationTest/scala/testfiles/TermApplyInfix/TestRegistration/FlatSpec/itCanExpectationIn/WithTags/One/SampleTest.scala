package testfiles.TermApplyInfix.TestRegistration.FlatSpec.itCanExpectationIn.WithTags.One

import org.scalatest.Tag
import org.scalatest.flatspec.AnyFlatSpec

class SampleTest extends AnyFlatSpec {

  it can "save" taggedAs Tag("MyTag") in {
    val x = 3
  }
}