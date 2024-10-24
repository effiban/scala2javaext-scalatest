package testfiles.TermApply.BasicAssertion.assertThrows.Basic

import org.scalatest.Assertions.assertThrows

class SampleTest {

  def dummy(): Unit = {
    assertThrows[ArithmeticException] {
      val x = 1 / 0
    }
  }
}