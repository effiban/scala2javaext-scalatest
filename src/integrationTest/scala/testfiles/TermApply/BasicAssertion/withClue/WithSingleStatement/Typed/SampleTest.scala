package testfiles.TermApply.BasicAssertion.withClue.WithSingleStatement.Typed

import org.scalatest.Assertions.withClue

class SampleTest {

  def calculateShouldReturnResult(): Int = {
    withClue[Int]("error in calculation") {
      1 + 2
    }
  }
}