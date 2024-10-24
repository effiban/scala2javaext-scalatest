package testfiles.TermApplyInfix.MatcherAssertion.should.have.size.asTermApplyInfix

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = List("a", "b", "c")
    result should have size 3
  }
}