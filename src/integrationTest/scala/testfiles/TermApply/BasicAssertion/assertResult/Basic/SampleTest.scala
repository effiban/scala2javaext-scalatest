package testfiles.TermApply.BasicAssertion.assertResult.Basic

import org.scalatest.Assertions.assertResult


class SampleTest {

  def dummy(): Unit = {
    val (x, y) = (1, 2)
    assertResult(3)(x + y)
  }
}