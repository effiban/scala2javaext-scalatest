package testfiles.TermApplyInfix.MatcherAssertion.should.endWith.regex

import org.scalatest.matchers.should.Matchers

class SampleTest extends Matchers {

  def dummy(): Unit = {
    val result = "123abc"
    result should endWith regex "[a-z]+"
  }
}