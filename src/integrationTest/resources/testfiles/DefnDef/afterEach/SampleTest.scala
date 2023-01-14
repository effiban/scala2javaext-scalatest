package dummy

import org.scalatest._

class SampleTest extends BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    cleanup()
  }
}