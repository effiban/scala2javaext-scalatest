package testfiles.TermApplyInfix.MatcherAssertion.should.not.have.size

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List("a", "b", "c", "d")
    result should not have size(3)
  }
}