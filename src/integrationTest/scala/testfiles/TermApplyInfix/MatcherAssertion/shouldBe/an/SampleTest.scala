package testfiles.TermApplyInfix.MatcherAssertion.shouldBe.an

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = 3
    result shouldBe an[Int]
  }
}