package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    result should (contain("a") or have size 4)
  }
}