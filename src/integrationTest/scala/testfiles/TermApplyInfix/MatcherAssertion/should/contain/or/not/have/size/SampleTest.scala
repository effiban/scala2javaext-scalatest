package testfiles.TermApplyInfix.MatcherAssertion.should.contain.or.not.have.size

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List("a", "b")
    result should (contain("a") or not have size (4))
  }
}