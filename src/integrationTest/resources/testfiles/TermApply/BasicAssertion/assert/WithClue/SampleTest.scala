package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    assert(x == 3, "x should be 3")
  }
}