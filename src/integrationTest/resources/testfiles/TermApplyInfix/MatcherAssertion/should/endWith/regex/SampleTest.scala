package dummy

import org.scalatest._

class SampleTest {

  def dummy(): Unit = {
    result should endWith regex "[a-z]+"
  }
}