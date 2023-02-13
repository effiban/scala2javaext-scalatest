package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    assertThrows[IllegalStateException] {
      doSomethingIllegal()
    }
  }
}