package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    assertResult(3) {
      val x = 1
      val y = 2
      x + y
    }
  }
}