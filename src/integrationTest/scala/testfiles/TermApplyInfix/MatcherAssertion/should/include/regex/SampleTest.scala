package testfiles.TermApplyInfix.MatcherAssertion.should.include.regex

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = "1abc1"
    result should include regex "[a-z]+"
  }
}