package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    result should (contain("a") or not have size (4))
  }
}