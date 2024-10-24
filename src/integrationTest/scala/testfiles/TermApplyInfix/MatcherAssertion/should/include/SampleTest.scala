package testfiles.TermApplyInfix.MatcherAssertion.should.include

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = "xabcx"
    result should include("abc")
  }
}