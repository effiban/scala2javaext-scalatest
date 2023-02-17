package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    withClue("special clue") {
      doSomething()
    }
  }
}