package testfiles.TermApplyInfix.MatcherAssertion.should.contain.allOf

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List(1, 2, 3, 4)
    result should contain allOf(3, 4)
  }
}