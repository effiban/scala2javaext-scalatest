package testfiles.TermApply.BasicAssertion.assertThrows.WithMultipleStatements

import org.scalatest.Assertions.assertThrows

class SampleTest {

  def dummy(): Unit = {
    assertThrows[ArithmeticException] {
      val x = 1
      1 / 0
    }
  }
}