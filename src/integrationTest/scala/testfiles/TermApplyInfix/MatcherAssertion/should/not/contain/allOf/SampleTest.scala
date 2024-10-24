package testfiles.TermApplyInfix.MatcherAssertion.should.not.contain.allOf

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List(1, 2)
    result should not contain allOf(3, 4)
  }
}