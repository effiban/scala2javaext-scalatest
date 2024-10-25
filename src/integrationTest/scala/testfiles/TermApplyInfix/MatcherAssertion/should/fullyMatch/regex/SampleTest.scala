package testfiles.TermApplyInfix.MatcherAssertion.should.fullyMatch.regex

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = "abc"
    result should fullyMatch regex "[a-z]+"
  }
}