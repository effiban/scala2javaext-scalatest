package testfiles.TermApplyInfix.MatcherAssertion.should.be.lessOrEqual

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = 3
    result should be <= 3
  }
}