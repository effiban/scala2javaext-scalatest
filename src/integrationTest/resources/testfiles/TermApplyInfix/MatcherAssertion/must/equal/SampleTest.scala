package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    result must equal(3)
  }
}