package testfiles.TermApply.BasicAssertion.intercept.Basic

import org.scalatest.Assertions.intercept

class SampleTest {

  def dummy(): Unit = {
    val exception = intercept[ArithmeticException] {
      1 / 0
    }
  }
}