package testfiles.TermApplyInfix.MatcherAssertion.should.not.contain.and.have.size

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List("a", "b")
    result should not (contain("a") and have size 4)
  }
}