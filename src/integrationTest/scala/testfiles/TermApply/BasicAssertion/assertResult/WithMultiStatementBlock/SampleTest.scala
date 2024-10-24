package testfiles.TermApply.BasicAssertion.assertResult.WithMultiStatementBlock

import org.scalatest.Assertions.assertResult

class SampleTest {

  def dummy(): Unit = {
    assertResult(3) {
      val x = 1
      val y = 2
      x + y
    }
  }
}