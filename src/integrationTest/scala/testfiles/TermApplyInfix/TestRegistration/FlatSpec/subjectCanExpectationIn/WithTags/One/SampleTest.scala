package testfiles.TermApplyInfix.TestRegistration.FlatSpec.subjectCanExpectationIn.WithTags.One

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

class SampleTest extends AnyFlatSpec {

  "process" can "save" taggedAs Tag("MyTag") in {
    val x = 3
  }
}