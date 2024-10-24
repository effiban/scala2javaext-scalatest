package testfiles.TermApply.BasicAssertion.Assert.Basic

import org.scalatest.Assertions.assert

class SampleTest {

  def dummy(): Unit = {
    val x = 3
    assert(x == 3)
  }
}