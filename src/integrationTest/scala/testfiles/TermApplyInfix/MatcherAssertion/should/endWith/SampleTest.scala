package testfiles.TermApplyInfix.MatcherAssertion.should.endWith

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = "xxxabc"
    result should endWith("abc")
  }
}