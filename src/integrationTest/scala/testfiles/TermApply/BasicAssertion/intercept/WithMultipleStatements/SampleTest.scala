package testfiles.TermApply.BasicAssertion.intercept.WithMultipleStatements

import org.scalatest.Assertions.intercept

class SampleTest {

  def dummy(): Unit = {
    val exception = intercept[ArithmeticException] {
      val x = 1
      val y = 1 / 0
    }
  }
}