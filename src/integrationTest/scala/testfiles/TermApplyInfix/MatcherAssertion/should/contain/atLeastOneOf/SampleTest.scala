package testfiles.TermApplyInfix.MatcherAssertion.should.contain.atLeastOneOf

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List(3, 5)
    result should contain atLeastOneOf(3, 4)
  }
}