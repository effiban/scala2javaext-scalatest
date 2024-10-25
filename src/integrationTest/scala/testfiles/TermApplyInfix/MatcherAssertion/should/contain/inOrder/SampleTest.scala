package testfiles.TermApplyInfix.MatcherAssertion.should.contain.inOrder

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List(3, 4, 5)
    result should contain inOrder(3, 4)
  }
}