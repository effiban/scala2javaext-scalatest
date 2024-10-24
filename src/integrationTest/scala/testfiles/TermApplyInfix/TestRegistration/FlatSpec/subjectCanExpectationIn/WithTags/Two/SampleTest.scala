package testfiles.TermApplyInfix.TestRegistration.FlatSpec.subjectCanExpectationIn.WithTags.Two

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

class SampleTest extends AnyFlatSpec {

  "process" can "save" taggedAs(Tag("Tag1"), Tag("Tag2")) in {
    val x = 3
  }
}