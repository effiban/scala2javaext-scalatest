package testfiles.TermApplyInfix.MatcherAssertion.shouldBe.empty

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = Nil
    result shouldBe empty
  }
}