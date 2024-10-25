package testfiles.TermApplyInfix.MatcherAssertion.should.contain.theSameElementsAs

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val expectedList = List(2, 1)
    val result = List(1, 2)
    result should contain theSameElementsAs expectedList
  }
}