package testfiles.TermApplyInfix.TestRegistration.FlatSpec.itCanExpectationIn.WithTags.Two

import org.scalatest.Tag
import org.scalatest.flatspec.AnyFlatSpec

class SampleTest extends AnyFlatSpec {

  it can "save" taggedAs(Tag("Tag1"), Tag("Tag2")) in {
    val x = 3
  }
}