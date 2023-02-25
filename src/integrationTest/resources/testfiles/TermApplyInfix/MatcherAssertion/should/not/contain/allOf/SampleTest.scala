package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    result should not contain allOf(3, 4)
  }
}