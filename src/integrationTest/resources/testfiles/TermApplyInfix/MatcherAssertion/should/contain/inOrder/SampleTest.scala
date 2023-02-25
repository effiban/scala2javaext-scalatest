package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    result should contain inOrder(3, 4)
  }
}