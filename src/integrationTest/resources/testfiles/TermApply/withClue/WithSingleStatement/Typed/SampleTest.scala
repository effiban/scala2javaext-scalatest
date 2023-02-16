package dummy

import org.scalatest._

class SampleTest {

  def calculateShouldReturnResult(): Int = {
    withClue[Int]("error in calculation") {
      calculate()
    }
  }
}