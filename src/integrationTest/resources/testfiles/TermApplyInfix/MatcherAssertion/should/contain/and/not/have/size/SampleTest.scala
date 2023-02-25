package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    result should (contain("a") and not have size (4))
  }
}