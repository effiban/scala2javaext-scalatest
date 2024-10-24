package testfiles.TermApplyInfix.MatcherAssertion.should.contain.or.have.size

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List("a", "b")
    result should (contain("a") or have size 4)
  }
}