package testfiles.TermApplyInfix.MatcherAssertion.should.be.greaterThan

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = 4
    result should be > 3
  }
}