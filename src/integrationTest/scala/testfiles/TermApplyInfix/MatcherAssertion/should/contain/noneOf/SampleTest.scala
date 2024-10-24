package testfiles.TermApplyInfix.MatcherAssertion.should.contain.noneOf

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List(1, 2)
    result should contain noneOf(3, 4)
  }
}