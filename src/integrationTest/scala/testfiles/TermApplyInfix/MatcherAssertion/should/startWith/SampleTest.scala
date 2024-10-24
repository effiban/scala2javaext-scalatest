package testfiles.TermApplyInfix.MatcherAssertion.should.startWith

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = "abcd"
    result should startWith("abc")
  }
}