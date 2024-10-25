package testfiles.TermApply.BasicAssertion.withClue.WithMultipleStatements

import org.scalatest.Assertions.withClue

class SampleTest {

  def dummy(): Unit = {
    withClue("special clue") {
      val x = 1
      1 / 0
    }
  }
}