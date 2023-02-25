package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    result should not (contain("a") and have size 4)
  }
}