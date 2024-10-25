package testfiles.TermApplyInfix.MatcherAssertion.should.contain.and.not.have.size

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List("a", "b")
    result should (contain("a") and not have size (4))
  }
}