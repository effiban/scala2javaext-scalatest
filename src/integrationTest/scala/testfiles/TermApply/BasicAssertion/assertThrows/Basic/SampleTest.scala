package testfiles.TermApply.BasicAssertion.assertThrows.Basic

import org.scalatest.Assertions.assertThrows

class SampleTest {

  def dummy(): Unit = {
    assertThrows[ArithmeticException] {
      1 / 0
    }
  }
}