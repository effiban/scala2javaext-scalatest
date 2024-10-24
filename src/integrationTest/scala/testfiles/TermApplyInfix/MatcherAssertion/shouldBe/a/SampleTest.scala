package testfiles.TermApplyInfix.MatcherAssertion.shouldBe.a

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = "abc"
    result shouldBe a[String]
  }
}