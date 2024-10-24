package testfiles.TermApplyInfix.MatcherAssertion.should.contain.theSameElementsInOrderAs

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val expectedList = List(1, 2)
    val result = List(1, 2)
    result should contain theSameElementsInOrderAs expectedList
  }
}