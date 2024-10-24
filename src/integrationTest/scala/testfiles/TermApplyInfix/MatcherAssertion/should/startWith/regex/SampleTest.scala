package testfiles.TermApplyInfix.MatcherAssertion.should.startWith.regex

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = "abc123"
    result should startWith regex "[a-z]+"
  }
}