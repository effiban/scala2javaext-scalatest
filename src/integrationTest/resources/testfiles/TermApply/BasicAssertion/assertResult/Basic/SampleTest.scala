package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    assertResult(3)(x + y)
  }
}