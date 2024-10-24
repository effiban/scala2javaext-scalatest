package testfiles.TermApplyInfix.MatcherAssertion.should.contain

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List(1, 2, 3)
    result should contain(3)
  }
}