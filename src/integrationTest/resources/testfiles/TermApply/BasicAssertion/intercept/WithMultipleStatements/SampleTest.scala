package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    val exception = intercept[IllegalStateException] {
      doSomethingLegal()
      doSomethingIllegal()
    }
  }
}