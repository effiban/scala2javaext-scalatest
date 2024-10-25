package testfiles.TermApplyInfix.MatcherAssertion.mustBe

import org.scalatest.matchers.must.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = 3
    result mustBe 3
  }
}