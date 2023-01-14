package dummy

import org.scalatest._

class SampleTest extends BeforeAndAfterAll {

  override protected def afterAll(): Unit = {
    cleanup()
  }
}