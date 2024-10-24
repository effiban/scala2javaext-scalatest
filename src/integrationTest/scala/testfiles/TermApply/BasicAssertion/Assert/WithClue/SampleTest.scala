package testfiles.TermApply.BasicAssertion.Assert.WithClue

import org.scalatest.Assertions.assert

class SampleTest {

  def dummy(): Unit = {
    val x = 3
    assert(x == 3, "x should be 3")
  }
}