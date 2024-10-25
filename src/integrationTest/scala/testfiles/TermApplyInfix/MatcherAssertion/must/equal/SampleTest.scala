package testfiles.TermApplyInfix.MatcherAssertion.must.equal

import org.scalatest.matchers.must.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = 3
    result must equal(3)
  }
}