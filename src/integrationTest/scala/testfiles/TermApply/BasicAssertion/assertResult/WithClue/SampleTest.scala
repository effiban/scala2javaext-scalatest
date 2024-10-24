package testfiles.TermApply.BasicAssertion.assertResult.WithClue

import org.scalatest.Assertions.assertResult

class SampleTest {

  def dummy(): Unit = {
    val (x, y) = (1, 2)
    assertResult(3, "x + y should be 3") {
      x + y
    }
  }
}