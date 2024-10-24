package testfiles.TermApplyInfix.MatcherAssertion.should.contain.and.have.size

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List("a", "b", "c", "d")
    result should (contain("a") and have size 4)
  }
}