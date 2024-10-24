package testfiles.TermApplyInfix.MatcherAssertion.should.be.lessThan

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = 2
    result should be < 3
  }
}