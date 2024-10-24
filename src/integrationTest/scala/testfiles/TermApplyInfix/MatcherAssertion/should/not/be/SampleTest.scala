package testfiles.TermApplyInfix.MatcherAssertion.should.not.be

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = 2
    result should not be(3)
  }
}