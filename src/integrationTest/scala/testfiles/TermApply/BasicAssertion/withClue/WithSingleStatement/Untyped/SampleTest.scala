package testfiles.TermApply.BasicAssertion.withClue.WithSingleStatement.Untyped

import org.scalatest.Assertions.withClue

class SampleTest {

  def dummy(): Unit = {
    withClue("special clue") {
      1 + 2
    }
  }
}