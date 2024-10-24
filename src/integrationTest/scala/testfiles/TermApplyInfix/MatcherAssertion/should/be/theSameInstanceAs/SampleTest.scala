package testfiles.TermApplyInfix.MatcherAssertion.should.be.theSameInstanceAs

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val expected = new Object()
    val result = expected
    result should be theSameInstanceAs expected
  }
}